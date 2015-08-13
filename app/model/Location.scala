package model

import play.api.libs.json._


case class LatLong(latitude: Double, longitude: Double)

object LatLong {
  implicit val latLongFormat = Json.format[LatLong]
}

case class Location(name: String, latLong: LatLong, precision: Double)

object Location {
  implicit val locationFormat = Json.format[Location]
}
