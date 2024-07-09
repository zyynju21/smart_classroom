(global["webpackJsonp"]=global["webpackJsonp"]||[]).push([["pages/library/library"],{"01b6":function(t,e,r){"use strict";var i;r.d(e,"b",(function(){return a})),r.d(e,"c",(function(){return n})),r.d(e,"a",(function(){return i}));var a=function(){var t=this,e=t.$createElement;t._self._c},n=[]},f826:function(t,e,r){"use strict";r.r(e);var i=r("01b6"),a=r("fae9");for(var n in a)"default"!==n&&function(t){r.d(e,t,(function(){return a[t]}))}(n);var u,c=r("f0c5"),s=Object(c["a"])(a["default"],i["b"],i["c"],!1,null,null,null,!1,i["a"],u);e["default"]=s.exports},fae9:function(t,e,r){"use strict";r.r(e);var i=r("faf2"),a=r.n(i);for(var n in i)"default"!==n&&function(t){r.d(e,t,(function(){return i[t]}))}(n);e["default"]=a.a},faf2:function(t,e,r){"use strict";Object.defineProperty(e,"__esModule",{value:!0}),e.default=void 0;var i={name:"basics",data:function(){return{libraryList:[],noticeMsg:{id:0,title:""},cardCur:0,swiperList:[{id:0,type:"image",url:"../../static/tushu/1.png"},{id:1,type:"image",url:"../../static/tushu/2.png"}],dotStyle:!1,towerStart:0,direction:""}},mounted:function(){console.log(this.$config.apiUrl),this.getLibrary()},methods:{getLibrary:function(){var t=this;this.$http.post("/wx/libraryType/list",{current:1,size:20}).then((function(e){"200"===e.data.code&&(t.libraryList=e.data.data.records)}))},detailBtn:function(t){this.$Router.push({path:"/pages/basics/detail",query:{id:t}})}}};e.default=i}}]);
;(global["webpackJsonp"] = global["webpackJsonp"] || []).push([
    'pages/library/library-create-component',
    {
        'pages/library/library-create-component':(function(module, exports, __webpack_require__){
            __webpack_require__('543d')['createComponent'](__webpack_require__("f826"))
        })
    },
    [['pages/library/library-create-component']]
]);
