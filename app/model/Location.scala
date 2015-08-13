package model

import play.api.libs.json._
import play.api.libs.functional.syntax._

case class LatLong(latitude: Double, longitude: Double)

object LatLong {
  implicit val latLongFormat = Json.format[LatLong]
}

case class Location(name: String, latLong: LatLong, precision: Double, timestamp : Long)

object Location {

  implicit val locationReads: Reads[Location] = new Reads[Location] {
    override def reads(json: JsValue): JsResult[Location] = {
      val name = (json \ "name").as[String]
      val precision = (json \ "precision").as[Double]
      val latLong = (json \ "latLong").as[LatLong]
      JsSuccess(Location(name, latLong, precision, System.currentTimeMillis()))
    }
  }

  implicit val locationWrites : Writes[Location] = Json.writes[Location]
}
