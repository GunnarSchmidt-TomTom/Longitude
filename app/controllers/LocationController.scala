package controllers


import javax.inject.Inject

import com.evojam.mongodb.client.MongoClient
import model.Location
import play.api.libs.json.{Json, JsError, JsSuccess}
import play.api.mvc.{Action, Controller}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import com.evojam.mongodb.play.json._

class LocationController @Inject()(mongo: MongoClient) extends Controller {

  def collection = mongo.database().collection("longitude")

  def postLocation = Action.async(parse.json) {
    request =>
      val json = request.body.validate[Location]

      json match {
        case JsSuccess(location, _) =>
          collection.insert(location).map(_ => Ok)
        case JsError(exception) =>
          println(exception)
          Future(BadRequest)
      }

  }

  def getLocation(name : String) = Action.async {
    request =>
      collection.find(Json.obj("name" -> name)).collect[Location].map {
        result =>
          Ok(result.toString())
      }

  }

}
