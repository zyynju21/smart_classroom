import App from './App'
import Vue from 'vue'
import store from 'store/index.js'
import tip from './common/util/tip.js'
import configService from './common/service/config.service.js'

/* https://hhyang.cn/v2/ */
import router from './common/router'
import {RouterMount} from './plugin/uni-simple-router/index.js'


//store
Vue.prototype.$store=store

//tip
Vue.prototype.$tip=tip

//config
Vue.prototype.$config=configService

//request请求,指明是哪个对象方法
import { http } from '@/common/service/service.js' 
Vue.prototype.$http=http

//注册导航
import basics from 'pages/basics/basics.vue'
Vue.component('basics',basics)


import library from 'pages/library/library.vue'
Vue.component("library",library)

import books from 'pages/books/books.vue'
Vue.component("books",books)



import about from 'pages/about/about.vue'
Vue.component("about",about)

//返回导航页
import cuCustom from 'plugin/colorui/components/cu-custom.vue'
Vue.component('cu-custom',cuCustom)

// 引入分隔线
import divider from "@/components/common/divider.vue"
Vue.component('divider',divider)



Vue.config.productionTip = false
App.mpType = 'app'
const app = new Vue({
	store,
    ...App
})

//v1.3.5起 H5端 你应该去除原有的app.$mount();使用路由自带的渲染方式
// #ifdef H5
	RouterMount(app,'#app');
// #endif

// #ifndef H5
	app.$mount(); //为了兼容小程序及app端必须这样写才有效果
// #endif
