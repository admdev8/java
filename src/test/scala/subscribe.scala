
package com.pubnub.api.tests

import org.json.{JSONArray, JSONObject}
import org.scalatest.fixture
import org.scalatest.{BeforeAndAfterAll, fixture, Tag}
import com.jayway.awaitility.scala.AwaitilitySupport
import org.junit._
import Assert._


import java.util.concurrent.TimeUnit.MILLISECONDS

import com.jayway.awaitility.Awaitility._
import com.jayway.awaitility.core.ConditionTimeoutException


import com.pubnub.api._


import java.util.concurrent.TimeUnit
import  com.jayway.awaitility.Awaitility.await


import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import scala.util.Try



import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import scala.util.Random

class PubnubTestConfig {
  var pubnub: Pubnub = null
  var unicode: Boolean = false
  var filter:  String = null
  var metadata: JSONObject = null
}


class PnTest(checks: Int) {

  var checksCount: Int = checks

  def test(check: Boolean) = {
    if (check == true) {
      checksCount -= 1
    }
  }
  def checksRemaining(): Int = {
    return checksCount
  }
}


@RunWith(classOf[JUnitRunner])
class SubscribeSpec extends fixture.FunSpec with AwaitilitySupport {

  var PUBLISH_KEY   = ""
  var SUBSCRIBE_KEY = ""
  var SECRET_KEY    = ""
  var CIPHER_KEY    = ""
  var SSL           = false
  var RANDOM        = new Random()



  type FixtureParam = PubnubTestConfig

  def getRandom(): String = {
    return RANDOM.nextInt().toString
  }

  def withFixture(test: OneArgTest) {
    var pubnubTestConfig = new PubnubTestConfig()
    PUBLISH_KEY = test.configMap.getRequired[String]("publish_key").asInstanceOf[String]
    SUBSCRIBE_KEY = test.configMap.getRequired[String]("subscribe_key").asInstanceOf[String]
    SECRET_KEY = test.configMap.getRequired[String]("secret_key").asInstanceOf[String]
    var cipher = test.configMap.getOptional[String]("cipher_key")
    if (cipher != scala.None) {
      CIPHER_KEY = test.configMap.getRequired[String]("cipher_key").asInstanceOf[String]
    }
    SSL = Try(test.configMap.getRequired[String]("ssl").asInstanceOf[String].toBoolean).getOrElse(false)
    val pubnub = new Pubnub(PUBLISH_KEY, SUBSCRIBE_KEY, SECRET_KEY, CIPHER_KEY, SSL)
    pubnubTestConfig.pubnub = pubnub
    withFixture(test.toNoArgTest(pubnubTestConfig))
  }

  describe("Subscribe()") {

    it("should be able to receive String message successfully") { pubnubTestConfig =>

      var pubnub  = pubnubTestConfig.pubnub
      var channel = "channel-" + getRandom()
      var message = "message-" + getRandom()
      var testObj = new PnTest(4)

      pubnub.subscribe(channel, new Callback {
        override def connectCallback(channel: String, message1: Object): Unit = {
          testObj.test(true)
          pubnub.publish(channel, message, new Callback {

            override def successCallback(channel: String, message: Object) {
              testObj.test(true)
            }

            override def errorCallback(channel: String, error: PubnubError) {
              testObj.test(false)
            }
          })

        }
        override def successCallback(channel: String, message1: Object) {
          pubnub.unsubscribe(channel)
          testObj.test(true)
          testObj.test(message1.equals(message))
        }
      });

      await atMost(5000, MILLISECONDS) until { testObj.checksRemaining() == 0 }

    }
    it("should be able to receive JSONObject message successfully") { pubnubTestConfig =>

      var pubnub  = pubnubTestConfig.pubnub
      var channel = "channel-" + getRandom()
      var messageStr = "message-" + getRandom()
      var message = new JSONObject()
      var testObj = new PnTest(3)

      message.put("message", messageStr)

      pubnub.subscribe(channel, new Callback {
        override def connectCallback(channel: String, message1: Object): Unit = {
          testObj.test(true)
          pubnub.publish(channel, message, new Callback {

            override def successCallback(channel: String, message: Object) {
              testObj.test(true)
            }

            override def errorCallback(channel: String, error: PubnubError) {
              testObj.test(false)
            }
          })

        }
        override def successCallback(channel: String, message1: Object) {
          pubnub.unsubscribe(channel)
          testObj.test(true)
          assertEquals(message.get("message"), message1.asInstanceOf[JSONObject].get("message"))
        }
      });

      await atMost(5000, MILLISECONDS) until { testObj.checksRemaining() == 0 }
    }
    it("should be able to receive JSONArray message successfully") { pubnubTestConfig =>

      var pubnub  = pubnubTestConfig.pubnub

      var channel = "channel-" + getRandom()
      var messageStr = "message-" + getRandom()
      var message = new JSONArray()
      var testObj = new PnTest(3)

      message.put("a")
      message.put("b")

      pubnub.subscribe(channel, new Callback {
        override def connectCallback(channel: String, message1: Object): Unit = {
          testObj.test(true)
          pubnub.publish(channel, message, new Callback {

            override def successCallback(channel: String, message: Object) {
              testObj.test(true)
            }

            override def errorCallback(channel: String, error: PubnubError) {
              testObj.test(false)
            }
          })

        }
        override def successCallback(channel: String, message1: Object) {
          pubnub.unsubscribe(channel)
          testObj.test(true)
          assertEquals(message.length(), message1.asInstanceOf[JSONArray].length())
          assertEquals(message.get(0), message1.asInstanceOf[JSONArray].get(0))
          assertEquals(message.get(1), message1.asInstanceOf[JSONArray].get(1))
        }
      });

      await atMost(5000, MILLISECONDS) until { testObj.checksRemaining() == 0 }
    }
  }
}