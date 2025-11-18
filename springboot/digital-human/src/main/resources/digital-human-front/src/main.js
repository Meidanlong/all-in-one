import Vue from 'vue'
import App from './App'
import Router from 'vue-router'
import VoiceChat from './components/VoiceChat'

Vue.config.productionTip = false

// 简单的路由实现
Vue.use(Router)

const router = new Router({
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

new Vue({
    router,
    render: h => h(App)
}).$mount('#app')