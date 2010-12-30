package hackworld.jmespike

import com.jme3.app.SimpleApplication
import com.jme3.scene.Geometry
import com.jme3.material.Material
import com.jme3.math.{Vector3f, ColorRGBA}
import com.jme3.light.DirectionalLight
import com.jme3.asset.plugins.FileLocator

import scala.math._
import com.jme3.scene.shape.{Cylinder, Box}
import com.jme3.input.KeyInput
import com.jme3.input.controls.{AnalogListener, KeyTrigger}
import java.lang.String
import com.jme3.terrain.heightmap.{ImageBasedHeightMap, AbstractHeightMap}
import com.jme3.texture.Texture.WrapMode
import jme3tools.converters.ImageToAwt
import java.util.ArrayList
import com.jme3.terrain.geomipmap.{TerrainLodControl, TerrainQuad}
import com.jme3.renderer.Camera

/**
 * 
 */
object JmeTest extends SimpleApplication {

  val Tau: Float = 2 * Pi.toFloat

  private var terrain: TerrainQuad  = null
  private var mat_terrain: Material  = null

  def main(args: Array[ String ])
  {
    println("Hello world")

    start()
  }

  private var player: Geometry = null

  def simpleInitApp {
    assetManager.registerLocator("assets", classOf[FileLocator])
    rootNode.detachAllChildren

    flyCam.setMoveSpeed(50);

//     val b = new Box(Vector3f.ZERO, 2, 3, 1)
     val b = new Cylinder(4, 20, 1, 2, true)
     player = new Geometry("Box", b)
     val mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md")
    mat.setBoolean("m_UseMaterialColors", true);
    mat.setColor("m_Ambient",  ColorRGBA.DarkGray);
    mat.setColor("m_Diffuse",  ColorRGBA.White);
    mat.setColor("m_Specular", ColorRGBA.Pink);
    mat.setFloat("m_Shininess", 12);

     //     val mat = new Material(assetManager, "Common/MatDefs/Misc/SolidColor.j3md")
  //   val mat = new Material(assetManager, "Common/MatDefs/Misc/SimpleTextured.j3md")
//     val mat = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md")
//     mat.setColor("m_Color", ColorRGBA.Blue)
    mat.setTexture("m_DiffuseMap", assetManager.loadTexture("textures/purple.jpg"))
//    mat.setTexture("m_NormalMap", assetManager.loadTexture("textures/lava.jpg"))
     player.setMaterial(mat)
     rootNode.attachChild(player)


     // Add light to show scene
     val sun = new DirectionalLight();
     sun.setDirection(new Vector3f(-0.1f, -0.7f, -1.0f).normalizeLocal);
     rootNode.addLight(sun);

    player.rotate(Tau/4, 0,0)

    initTerrain()

    initKeys()
  }

  var t = 0.0

  override def simpleUpdate(tpf: Float) {
    t += tpf

    player.setLocalScale(1, 1, (sin(t*Tau*1.5)*1).toFloat/3f+1f)

    player.rotate(tpf/3.2f, tpf, tpf*4.23f)
  }

  private def initKeys() {

    inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_LEFT))
    inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_J))
    inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_RIGHT))
    inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_L))
    inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_UP))
    inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_I))
    inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_DOWN))
    inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_K))
    inputManager.addMapping("Action1", new KeyTrigger(KeyInput.KEY_SPACE))
    inputManager.addMapping("Action1", new KeyTrigger(KeyInput.KEY_RETURN))
    inputManager.addMapping("Action2", new KeyTrigger(KeyInput.KEY_RSHIFT))
    inputManager.addMapping("Action2", new KeyTrigger(KeyInput.KEY_E))

    inputManager.addListener(directionListener, "Left", "Right", "Up", "Down")
  }

  val moveSpeed = 1000f

  private val directionListener = new AnalogListener() {
    def onAnalog(name: String, value: Float, tpf: Float) {
      val delta = value * moveSpeed * tpf
      name match {
        case "Right" => player.move( delta, 0, 0)
        case "Left"  => player.move(-delta, 0, 0)
        case "Up"    => player.move(0, 0,  delta)
        case "Down"  => player.move(0, 0, -delta)
      }
    }
  }

  def initTerrain() {
    /** 1. Create terrain material and load four textures into it. */
    mat_terrain = new Material(assetManager, "Common/MatDefs/Terrain/Terrain.j3md");

    /** 1.1) Add ALPHA map (for red-blue-green coded splat textures) */
    mat_terrain.setTexture("m_Alpha",
               assetManager.loadTexture("Textures/Terrain/splat/alphamap.png"));

    /** 1.2) Add GRASS texture into the red layer (m_Tex1). */
    val grass = assetManager.loadTexture("textures/lava.jpg");
    grass.setWrap(WrapMode.Repeat);
    mat_terrain.setTexture("m_Tex1", grass);
    mat_terrain.setFloat("m_Tex1Scale", 16f);

    /** 1.3) Add DIRT texture into the green layer (m_Tex2) */
    val dirt = assetManager.loadTexture("textures/purple.jpg");
    dirt.setWrap(WrapMode.Repeat);
    mat_terrain.setTexture("m_Tex2", dirt);
    mat_terrain.setFloat("m_Tex2Scale", 16f);

    /** 1.4) Add ROAD texture into the blue layer (m_Tex3) */
    val rock = assetManager.loadTexture("textures/colorplasma.jpg");
    rock.setWrap(WrapMode.Repeat);
    mat_terrain.setTexture("m_Tex3", rock);
    mat_terrain.setFloat("m_Tex3Scale", 8f);

    /** 2. Create the height map */
    var heightmap: AbstractHeightMap = null;
    val heightMapImage = assetManager.loadTexture("Textures/Terrain/splat/mountains512.png");
    heightmap = new ImageBasedHeightMap( ImageToAwt.convert(heightMapImage.getImage(), false, true, 0));
    heightmap.load();

    /** 3. We have prepared material and heightmap. Now we create the actual terrain:
     * 3.1) We create a TerrainQuad and name it "my terrain".
     * 3.2) A good value for terrain tiles is 64x64 -- so we supply 64+1=65.
     * 3.3) We prepared a heightmap of size 512x512 -- so we supply 512+1=513.
     * 3.4) As LOD step scale we supply Vector3f(1,1,1).
     * 3.5) At last, we supply the prepared heightmap itself.
     */
    terrain = new TerrainQuad("my terrain", 65, 513, heightmap.getHeightMap());

    /** 4. We give the terrain its material, position & scale it, and attach it. */
    terrain.setMaterial(mat_terrain);
    terrain.setLocalTranslation(0, -100, 0);
    terrain.setLocalScale(2f, 1f, 2f);
    rootNode.attachChild(terrain);

    /** 5. The LOD (level of detail) depends on were the camera is: */
    val cameras = new ArrayList[Camera]();
    cameras.add(getCamera());
    val control = new TerrainLodControl(terrain, cameras);
    terrain.addControl(control);

  }

}

