package hackworld.spacegame.generators

import com.jme3.asset.AssetManager
import hackworld.spacegame.entity.Entity
import java.awt.Shape
import com.jme3.scene.shape.Box
import com.jme3.material.Material
import hackworld.spacegame.facets.{Controllable, Appearance}
import com.jme3.scene.{Mesh, Geometry}

/**
 * 
 */
class ShipGenerator extends Generator {

  def generate(assetManager: AssetManager): Entity = {

    val entity = new Entity

//    entity.addFacet(new Appearance(makeShipGeometry(assetManager)))
    entity.addFacet(new Controllable())

    entity
  }

  def makeShipGeometry(assetManager: AssetManager): Geometry = {

    val shape: Mesh = new Box(4, 10, 1)

    val geometry = new Geometry("ship", shape)

    val mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md")
    mat.setTexture("m_DiffuseMap", assetManager.loadTexture("textures/purple.jpg"))

    geometry.setMaterial(mat)

    geometry
  }

}

