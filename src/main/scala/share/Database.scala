package share


import cats.effect.Sync
import cats.implicits._
import doobie.implicits._
import doobie.util.transactor.Transactor

class Database[F[_]: Sync](transactor: Transactor[F]) {
  /**
   * Retrieve a list of notes from the database.
   *
   * @return A list of notes.
   */
  def getNotes: F[List[Note]] =
    sql"SELECT id, content FROM notes"
      .query[Note]
      .to[List]
      .transact(transactor)

  /**
   * Retrieve a note by its ID from the database.
   *
   * @param noteId The ID of the note to retrieve.
   * @return An optional note.
   */
  def getNoteById(noteId: Long): F[Option[Note]] =
    sql"SELECT id, content FROM notes WHERE id = $noteId"
      .query[Note]
      .option
      .transact(transactor)
  /**
   * Create a new note in the database.
   *
   * @param note The note to create.
   */
  def createNote(note: Note): F[Unit] =
    sql"INSERT INTO notes (id, content) VALUES (${note.id}, ${note.content})"
      .update
      .run
      .transact(transactor)
      .void

  /**
   * Update a note in the database.
   *
   * @param noteId      The ID of the note to update.
   * @param updatedNote The updated note data.
   */
  def updateNote(noteId: Long, updatedNote: Note): F[Unit] =
    sql"UPDATE notes SET content = ${updatedNote.content} WHERE id = $noteId"
      .update
      .run
      .transact(transactor)
      .void

  /**
   * Delete a note from the database by its ID.
   *
   * @param noteId The ID of the note to delete.
   */
  def deleteNote(noteId: Long): F[Unit] =
    sql"DELETE FROM notes WHERE id = $noteId"
      .update
      .run
      .transact(transactor)
      .void
}