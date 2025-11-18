import Vue from 'vue'

// 全局WebSocket事件总线
export const wsBus = new Vue()

let socket = null

// 获取当前页面的协议和主机名，动态构建WebSocket URL
const getWebSocketUrl = () => {
    const protocol = window.location.protocol === 'https:' ? 'wss:' : 'ws:'
    const host = window.location.host
    return `${protocol}//${host}/ws/frontend`
}

// 初始化WebSocket
export function initWebSocket() {
    const wsUrl = getWebSocketUrl()

    // 如果已有连接，先关闭
    if (socket) {
        socket.close()
    }

    // 创建新的WebSocket连接
    socket = new WebSocket(wsUrl)

    // 设置事件处理函数
    socket.onopen = () => {
        console.log('WebSocket连接成功')
        wsBus.$emit('ws-connected')
    }

    socket.onmessage = (event) => {
        try {
            // 尝试解析JSON消息
            const msg = JSON.parse(event.data)
            switch (msg.type) {
                case 'audio':
                    // 音频数据：通知播放
                    wsBus.$emit('play-audio', msg.data)
                    break
                case 'animation':
                    // 动画参数：通知渲染
                    wsBus.$emit('update-animation', msg.data)
                    break
                default:
                    console.log('未知消息类型：', msg.type)
            }
        } catch (e) {
            // 如果不是JSON格式，可能是纯音频数据
            console.log('接收到非JSON消息：', event.data.substring(0, 50) + '...')
        }
    }

    socket.onclose = () => {
        console.log('WebSocket连接关闭')
        wsBus.$emit('ws-disconnected')
    }

    socket.onerror = (error) => {
        console.error('WebSocket错误：', error)
        wsBus.$emit('ws-error', error)
    }
}

// 发送消息到后端
export function sendWsMessage(data) {
    if (socket && socket.readyState === WebSocket.OPEN) {
        socket.send(data)
    } else {
        console.error('WebSocket未连接，发送失败')
    }
}

// 关闭WebSocket连接
export function closeWebSocket() {
    if (socket) {
        socket.close()
    }
}
