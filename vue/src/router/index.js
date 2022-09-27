import Vue from 'vue'
import VueRouter from 'vue-router'
import Manage from "@/views/Manage";
import User from "@/views/User";
import Home from "@/views/Home";
import store from "../store";
import Login from "../views/Login";


Vue.use(VueRouter)

const routes = [
	{
		path: '/',
		name: 'manage',
		component: Manage,
		redirect:'/user',
		children:[
			{
				path:'user',
				name:'yonghu',
				component:User,
				meta:{title:'用户管理'},
				// props($route){
				// 	console.log($route)
				// 	return{
				// 		title:$route.meta.title
				// 	}
				// }
			},
			{
				path: 'home',
				name:'zhuye',
				component:Home,
				meta:{title:'主页'},
				// props($route){
				// 	console.log($route)
				// 	return{
				// 		title:$route.meta.title
				// 	}
				// }
			}
		]
	},
	{
		path: '/login',
		component: Login
	}
]

const router = new VueRouter({
	mode: 'history',
	base: process.env.BASE_URL,
	routes
})

router.beforeEach((to,from,next)=>{
	localStorage.setItem("currentPathName",to.meta.title)
	store.commit('UPDATETITLENAME')
	next();
})



export default router
