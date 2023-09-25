package share

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto._
import io.circe.Codec


case class Note(id: Long, content: String)

object Note {
  implicit val customConfig: Configuration = Configuration.default.withSnakeCaseMemberNames

  implicit val noteCodec: Codec[Note] = deriveConfiguredCodec
}