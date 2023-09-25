package share

import cats.implicits._
import org.http4s._
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import org.http4s.circe._
import cats.effect.Concurrent

class NoteService[F[_]: Concurrent](database: Database[F]) extends Http4sDsl[F] {

  implicit def noteEntityEncoder: EntityEncoder[F, Note] =
    jsonEncoderOf[F, Note]

  implicit def noteEntityDecoder: EntityDecoder[F, Note] =
    jsonOf[F, Note]

  implicit def notesEntityEncoder: EntityEncoder[F, List[Note]] =
    jsonEncoderOf[F, List[Note]]

  val routes: HttpRoutes[F] = HttpRoutes.of[F] {
    case GET -> Root / "notes" =>
      database.getNotes.flatMap(notes => Ok(notes))

    case GET -> Root / "notes" / LongVar(noteId) =>
      database.getNoteById(noteId).flatMap {
        case Some(note) => Ok(note)
        case None => NotFound()
      }

    case req @ POST -> Root / "notes" =>
      req.decode[Note] { note =>
        database.createNote(note).flatMap(_ => Created())
      }

    case req @ PUT -> Root / "notes" / LongVar(noteId) =>
      req.decode[Note] { note =>
        database.updateNote(noteId, note).flatMap(_ => NoContent())
      }

    case DELETE -> Root / "notes" / LongVar(noteId) =>
      database.deleteNote(noteId).flatMap(_ => NoContent())
  }
}