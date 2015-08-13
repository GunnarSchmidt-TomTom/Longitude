package controllers


import model.Location
import play.api.libs.json.{JsError, JsSuccess}
import play.api.mvc.{Action, Controller}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class LocationController extends Controller {

  def postLocation = Action.async(parse.json) {
    request =>
      val json = request.body.validate[Location]

      json match {
        case JsSuccess(location, _) =>
          println(location)
          Future(Ok)
        case JsError(exception) =>
          println(exception)
          Future(BadRequest)
      }

  }

}
