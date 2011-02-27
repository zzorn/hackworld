package hackworld.spacegame.entity

import com.jme3.asset.{AssetInfo, AssetLoader}
import java.io.BufferedInputStream
import org.codehaus.jackson.map.ObjectMapper
import org.codehaus.jackson.JsonNode

/**
 * Loads json formatted config files.
 */
class JsonConfigLoader extends AssetLoader {

  def load(assetInfo: AssetInfo): Object = {

    val stream = new BufferedInputStream(assetInfo.openStream)

    val om = new ObjectMapper()
    val rootNode = om.readValue(stream, classOf[JsonNode])

    stream.close

    rootNode
  }

}
