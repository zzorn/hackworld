package hackworld.spacegame

import java.util.List
import org.codehaus.jackson.{JsonGenerator, JsonNode}
import java.lang.String

/**
 * 
 */
object EmptyNode extends JsonNode {
  def traverse = null

  def writeTo(jg: JsonGenerator) {}

  def path(index: Int) = null

  def path(fieldName: String) = null

  def findParents(fieldName: String, foundSoFar: List[ JsonNode ]) = null

  def findValuesAsText(fieldName: String, foundSoFar: List[ String ]) = null

  def findValues(fieldName: String, foundSoFar: List[ JsonNode ]) = null

  def findParent(fieldName: String) = null

  def findPath(fieldName: String) = null

  def findValue(fieldName: String) = null

  def getValueAsText = ""

  def getNumberType = null

  def asToken = null

  override def getValueAsDouble(defaultValue: Double) = defaultValue
  override def getValueAsLong(defaultValue: Long) = defaultValue
  override def getValueAsInt(defaultValue: Int) = defaultValue

}
