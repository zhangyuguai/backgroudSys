import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from "./store";


import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

import request from "@/utils/request";

Vue.prototype.request=request;

Vue.use(ElementUI, {size: "mini"});
Vue.config.productionTip = false

new Vue({
	router,
	render: h => h(App),
	beforeCreate() {
		Vue.prototype.$bus=this
	},
	store
}).$mount('#app')
