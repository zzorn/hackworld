package hackworld.spacegame.facets

import org.codehaus.jackson.JsonNode
import com.jme3.scene.shape.{Sphere, Box}
import com.jme3.material.Material
import com.jme3.math.ColorRGBA
import scala.collection.JavaConversions._
import hackworld.NearSpace.SpaceGame
import com.jme3.scene.{Mesh, Spatial, Node, Geometry}
import hackworld.spacegame.EmptyNode

/**
 * 
 */
case class Appearance() extends Facet {

  var geometry: Spatial = null

  override def init(configData: JsonNode) {

    val node = new Node()

    configData.getElements() foreach { e =>
      node.attachChild(parseGeometry(e))
    }

    geometry = node

  }

  def parseGeometry(data: JsonNode): Spatial = {
    def getField(name: String): JsonNode = {
      if (data.has(name)) data.get(name)
      else EmptyNode
    }

    val shape: Mesh = data.get("shape").getValueAsText match {
      case "box" => new Box(
        getField("width").getValueAsDouble(1).toFloat,
        getField("height").getValueAsDouble(1).toFloat,
        getField("depth").getValueAsDouble(1).toFloat)
      case "sphere" => new Sphere(
        getField("zSamples").getValueAsInt(12),
        getField("radialSamples").getValueAsInt(12),
        getField("radius").getValueAsDouble(1).toFloat,
        getField("useEvenSlices").getBooleanValue,
        getField("interior").getBooleanValue)
      case v =>
        println("Unknown shape " + v)
        new Box(1,1,1)
    }

    // Get translation
    // TODO

    // Get scaling
    // TODO

    // Get rotation
    // TODO

    // Get material
    // TODO
    val mat = new Material(SpaceGame.getAssetManager, "Common/MatDefs/Light/Lighting.j3md")
    mat.setBoolean("m_UseMaterialColors", true);
    mat.setColor("m_Ambient", ColorRGBA.DarkGray);
    mat.setColor("m_Diffuse", ColorRGBA.LightGray);
    mat.setColor("m_Specular", ColorRGBA.Cyan);
    mat.setFloat("m_Shininess", 6);

    val geom = new Geometry("", shape)
    geom.setMaterial(mat)

    geom.setLocalTranslation(0,0,-1000)

    geom
  }

}