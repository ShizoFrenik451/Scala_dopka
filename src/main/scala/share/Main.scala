package share


import cats.effect.{ExitCode, IO, IOApp, Sync}
import org.http4s.implicits._
import org.http4s.server.Router
import org.http4s.blaze.server.BlazeServerBuilder
import cats.effect.kernel.Async
import fs2.Stream
import com.typesafe.config.ConfigFactory
import com.typesafe.config.Config

object Main extends IOApp {

  override def run(args: List[String]): IO[ExitCode] = {
    val config = ConfigFactory.defaultApplication()
    val databaseConfig = config.getConfig("database")
    TextSharingServer.stream[IO](databaseConfig).compile.drain.as(ExitCode.Success)
  }
}

object TextSharingServer {

  def stream[F[_]: Async: Sync](config: Config): Stream[F, ExitCode] = {
    val databaseConfig = config.getConfig("database")
    val transactorResource = DatabaseConfig.createTransactor[F](databaseConfig)

    Stream.resource(transactorResource).flatMap { transactor =>
      val database = new Database[F](transactor)
      val noteService = new NoteService[F](database)
      val httpApp = Router("/" -> noteService.routes).orNotFound

      BlazeServerBuilder[F]
        .bindHttp(8080, "0.0.0.0")
        .withHttpApp(httpApp)
        .serve
        .evalMap(_ => Sync[F].pure(ExitCode.Success))
    }
  }
}

