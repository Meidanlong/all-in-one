import Vue from 'vue'
import Router from 'vue-router'
import VoiceChat from '@/components/VoiceChat'

Vue.use(Router)

export default new Router({
    mode: 'history',
    base: '/app',
    routes: [
        {
            path: '/',
            name: 'VoiceChat',
            component: VoiceChat
        }
    ]
})

