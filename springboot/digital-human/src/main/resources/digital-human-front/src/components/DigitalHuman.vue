<template>
  <div class="digital-human-container">
    <canvas id="humanCanvas" width="800" height="600"></canvas>
  </div>
</template>

<script>
import * as THREE from 'three'
import {GLTFLoader} from 'three/examples/jsm/loaders/GLTFLoader'
import {wsBus} from '@/utils/websocket'

export default {
  name: 'DigitalHuman',
  data() {
    return {
      scene: null,
      camera: null,
      renderer: null,
      humanModel: null,
      blendShapes: {}, // 存储面部表情参数
      animationFrameId: null
    }
  },
  mounted() {
    this.initThree()
    this.loadModel()
    this.listenAnimationUpdate()
  },
  beforeDestroy() {
    if (this.animationFrameId) {
      cancelAnimationFrame(this.animationFrameId)
    }
    if (this.renderer) {
      this.renderer.dispose()
    }
  },
  methods: {
    // 初始化Three.js环境
    initThree() {
      // 1. 创建场景
      this.scene = new THREE.Scene()
      this.scene.background = new THREE.Color(0xf0f0f0)

      // 2. 创建相机
      this.camera = new THREE.PerspectiveCamera(75, 800 / 600, 0.1, 1000)
      this.camera.position.z = 3

      // 3. 创建渲染器
      const canvas = document.getElementById('humanCanvas')
      if (canvas) {
        this.renderer = new THREE.WebGLRenderer({canvas: canvas, antialias: true})
        this.renderer.setSize(800, 600)
        this.renderer.setPixelRatio(window.devicePixelRatio)
      }

      // 4. 添加光源
      const ambientLight = new THREE.AmbientLight(0xffffff, 0.8)
      this.scene.add(ambientLight)
      const directionalLight = new THREE.DirectionalLight(0xffffff, 0.5)
      directionalLight.position.set(5, 5, 5)
      this.scene.add(directionalLight)

      // 5. 动画循环
      const animate = () => {
        this.animationFrameId = requestAnimationFrame(animate)
        this.updateModelAnimation()
        if (this.renderer) {
          this.renderer.render(this.scene, this.camera)
        }
      }
      animate()
    },

    // 加载数字人3D模型（GLB格式）
    loadModel() {
      const loader = new GLTFLoader()
      // 注意：需要确保/public/model/目录下有avatar.glb文件
      loader.load('/model/avatar.glb', (gltf) => {
        this.humanModel = gltf.scene
        this.scene.add(this.humanModel)
        // 缩放模型
        this.humanModel.scale.set(2, 2, 2)
        this.humanModel.position.y = -1.5

        // 初始化BlendShapes（根据模型实际表情参数调整）
        if (gltf.scene.children[0] && gltf.scene.children[0].morphTargetDictionary) {
          this.blendShapes = gltf.scene.children[0].morphTargetDictionary
        }
        console.log('数字人模型加载成功')
      }, (xhr) => {
        console.log(`模型加载进度：${(xhr.loaded / xhr.total) * 100}%`)
      }, (error) => {
        console.error('模型加载失败：', error)
        // 如果模型加载失败，创建一个简单的立方体作为占位符
        const geometry = new THREE.BoxGeometry(1, 1, 1)
        const material = new THREE.MeshBasicMaterial({color: 0x00ff00})
        this.humanModel = new THREE.Mesh(geometry, material)
        this.humanModel.position.y = -1.5
        this.scene.add(this.humanModel)
        console.log('使用占位符模型')
      })
    },

    // 监听动画参数更新
    listenAnimationUpdate() {
      wsBus.$on('update-animation', (animationData) => {
        try {
          // 解析腾讯云返回的动画参数
          let animationParams
          if (typeof animationData === 'string') {
            animationParams = JSON.parse(animationData)
          } else {
            animationParams = animationData
          }

          const {BlendShapes, HeadPose} = animationParams
          if (BlendShapes && this.humanModel) {
            this.updateBlendShapes(BlendShapes)
          }
          if (HeadPose && this.humanModel) {
            this.updateHeadPose(HeadPose)
          }
        } catch (error) {
          console.error('动画参数解析失败：', error)
        }
      })
    },

    // 更新面部表情（BlendShapes）
    updateBlendShapes(blendShapeValues) {
      if (!this.humanModel || !this.humanModel.children[0]) return

      const model = this.humanModel.children[0]
      if (!model.morphTargetInfluences) return

      // 假设模型有52个表情参数，与腾讯云返回对应（需根据实际模型调整索引）
      blendShapeValues.forEach((value, index) => {
        if (model.morphTargetInfluences[index] !== undefined) {
          // 限制值在0-1范围内
          model.morphTargetInfluences[index] = Math.max(0, Math.min(1, value))
        }
      })
    },

    // 更新头部姿态
    updateHeadPose(headPose) {
      if (!this.humanModel) return

      const {Rotation, Translation} = headPose
      if (!Rotation || !Translation) return

      try {
        // 四元数转欧拉角
        const quaternion = new THREE.Quaternion(Rotation[0], Rotation[1], Rotation[2], Rotation[3])
        const euler = new THREE.Euler().setFromQuaternion(quaternion, 'XYZ')

        // 应用旋转和位移
        this.humanModel.rotation.x = euler.x
        this.humanModel.rotation.y = euler.y
        this.humanModel.rotation.z = euler.z
        this.humanModel.position.x = Translation[0]
        this.humanModel.position.y = Translation[1] + (-1.5) // 保持初始Y轴偏移
        this.humanModel.position.z = Translation[2]
      } catch (error) {
        console.error('头部姿态更新失败：', error)
      }
    },

    // 模型动画更新（空实现，实际由外部参数驱动）
    updateModelAnimation() {
      // 可以在这里添加额外的动画逻辑
    }
  }
}
</script>

<style scoped>
.digital-human-container {
  width: 800px;
  height: 600px;
  margin: 0 auto;
}

#humanCanvas {
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  width: 100%;
  height: 100%;
}
</style>