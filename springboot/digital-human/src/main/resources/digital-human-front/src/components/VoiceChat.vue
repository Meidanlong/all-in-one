<template>
  <div class="voice-chat-container">
    <h3>数字人语音对话</h3>
    <button @click="startRecord" :disabled="isRecording || !isConnected">开始录音</button>
    <button @click="stopRecord" :disabled="!isRecording || !isConnected">停止录音</button>
    <p>连接状态：{{ isConnected ? '已连接' : '未连接' }}</p>
    <p>录音状态：{{ isRecording ? '录音中...' : '未录音' }}</p>
    <DigitalHuman/>
  </div>
</template>

<script>
import DigitalHuman from './DigitalHuman'
import {closeWebSocket, initWebSocket, sendWsMessage, wsBus} from '@/utils/websocket'

export default {
  name: 'VoiceChat',
  components: {DigitalHuman},
  data() {
    return {
      isRecording: false,
      isConnected: false,
      mediaRecorder: null,
      audioChunks: [],
      audioContext: null
    }
  },
  mounted() {
    // 初始化WebSocket
    initWebSocket()
    // 监听WebSocket状态
    wsBus.$on('ws-connected', () => {
      this.isConnected = true
    })
    wsBus.$on('ws-disconnected', () => {
      this.isConnected = false
      this.isRecording = false
    })
    // 监听音频播放事件
    wsBus.$on('play-audio', (audioBase64) => {
      this.playAudio(audioBase64)
    })
  },
  methods: {
    // 开始录音
    async startRecord() {
      if (!this.isConnected) {
        alert('WebSocket未连接，请稍后再试')
        return
      }

      try {
        const stream = await navigator.mediaDevices.getUserMedia({
          audio: {
            sampleRate: 16000,
            channelCount: 1,
            echoCancellation: true,
            noiseSuppression: true
          }
        })

        // 使用MediaRecorder录制音频
        this.mediaRecorder = new MediaRecorder(stream, {
          mimeType: 'audio/webm;codecs=opus'
        })

        this.mediaRecorder.ondataavailable = (event) => {
          if (event.data.size > 0) {
            // 将音频数据转换为Base64并发送
            const reader = new FileReader()
            reader.onload = () => {
              // 移除Data URL前缀，只发送Base64数据
              const base64Data = reader.result.split(',')[1]
              // 发送音频分片到后端
              sendWsMessage(JSON.stringify({
                type: 'audio_chunk',
                data: base64Data
              }))
            }
            reader.readAsDataURL(event.data)
          }
        }

        this.mediaRecorder.onstop = () => {
          // 发送录音结束信号
          sendWsMessage(JSON.stringify({
            type: 'audio_end'
          }))

          // 停止所有音轨
          stream.getTracks().forEach(track => track.stop())
        }

        // 每200ms发送一次音频数据
        this.mediaRecorder.start(200)
        this.isRecording = true
        console.log('开始录音')
      } catch (error) {
        console.error('录音权限获取失败：', error)
        alert('请授予麦克风权限')
      }
    },

    // 停止录音
    stopRecord() {
      if (this.mediaRecorder && this.isRecording) {
        this.mediaRecorder.stop()
        this.isRecording = false
        console.log('停止录音')
      }
    },

    // 播放后端推送的音频
    playAudio(audioBase64) {
      try {
        // 创建临时的audio元素播放音频
        const audio = new Audio()
        audio.src = `data:audio/ogg;base64,${audioBase64}`
        audio.play().catch(e => {
          console.error('音频播放失败：', e)
        })
      } catch (error) {
        console.error('音频播放失败：', error)
      }
    }
  },
  beforeDestroy() {
    // 组件销毁前停止录音
    if (this.mediaRecorder && this.isRecording) {
      this.mediaRecorder.stop()
    }
    // 关闭WebSocket连接
    closeWebSocket()
  }
}
</script>