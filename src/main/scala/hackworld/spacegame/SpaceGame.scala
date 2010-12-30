package hackworld.NearSpace

import com.jme3.app.SimpleApplication
import com.jme3.scene.Node
import com.jme3.asset.plugins.FileLocator
import com.jme3.math.Vector3f
import com.jme3.light.DirectionalLight
import hackworld.spacegame.generators.{AsteroidGenerator, PlanetGenerator}
import scala.util.Random
import com.jme3.input.InputManager
import com.jme3.asset.AssetManager
import hackworld.spacegame.FacetManager
import hackworld.spacegame.facets._
import hackworld.spacegame.entity.{EntityBuilder, JsonConfigLoader, Entity}

/**
 * 
 */
object SpaceGame extends SimpleApplication {

  def main(args: Array[ String ])
  {
    start()
  }

  def simpleInitApp {
    FacetManager.registerFacetType[Appearance]
    FacetManager.registerFacetType[Controllable]
    FacetManager.registerFacetType[InputController]
    FacetManager.registerFacetType[Location]
    FacetManager.registerFacetType[Moving]
    FacetManager.registerFacetType[Thrustable]

    assetManager.registerLocator("assets", classOf[FileLocator])
    assetManager.registerLoader(classOf[JsonConfigLoader], "conf")


     // Add light to show scene
     val sun = new DirectionalLight();
     sun.setDirection(new Vector3f(-0.1f, -0.7f, -1.0f).normalizeLocal);
     rootNode.addLight(sun);

    generate()
  }


  def generate() {
    val spaceNode = new Node("Space")

    val e = EntityBuilder.createEntity("entities/gasplanet.conf")
    spaceNode attachChild e.facet[Appearance].geometry

    /*
    val planGen = new PlanetGenerator()
    val asteroidGen= new AsteroidGenerator()
    spaceNode attachChild planGen.generate(assetManager).facet[Appearance].geometry

    val r = new Random(123);
    for (val i <- 1 to 100) {
      val scatter = 1000
      def rpos: Float = (r.nextGaussian * scatter).toFloat
      val pos = new Vector3f(rpos, rpos, rpos)
      val asteroid: Entity = asteroidGen.generate(assetManager)
      asteroid.facet[Location].pos := pos
      spaceNode attachChild asteroid.facet[Appearance].geometry
    }

*/
    rootNode attachChild spaceNode

  }
  override def simpleUpdate(tpf: Float) {

  }

}
