package hackworld.spacegame.entity

import org.codehaus.jackson.JsonNode
import hackworld.NearSpace.SpaceGame
import com.jme3.asset.AssetKey
import scala.collection.JavaConversions._

/**
 * Creates an entity from the specified configuration file.
 */
object EntityBuilder {

  def createEntity(path: String): Entity = {

    val assetManager = SpaceGame.getAssetManager

    // Create entity
    val entity = new Entity

    // Get config for entity
    val entityNode = assetManager.loadAsset[JsonNode](new AssetKey(path))

    if (entityNode == null) throw new IllegalArgumentException("Path not found " + path)

    entityNode.getFieldNames() foreach { facetName: String =>
      // Create the facet
      FacetManager.createFacet(Symbol(facetName)) match {
        case None    => println("Unknown Facet: " + facetName + ", ignoring.")
        case Some(f) =>
          println("Adding facet: " + facetName + ".")
          println("  facet: " + f._1)
          println("  manifest: " + f._2)

          // Add facet to entity
          entity.addFacet(f._1)(f._2)

          // Initialize the facet
          f._1.init(entityNode.get(facetName))
      }
    }

    entity
  }



}

