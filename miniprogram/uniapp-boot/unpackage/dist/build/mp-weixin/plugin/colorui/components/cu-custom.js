(global["webpackJsonp"]=global["webpackJsonp"]||[]).push([["plugin/colorui/components/cu-custom"],{2608:function(t,n,a){"use strict";var u;a.d(n,"b",(function(){return e})),a.d(n,"c",(function(){return c})),a.d(n,"a",(function(){return u}));var e=function(){var t=this,n=t.$createElement;t._self._c},c=[]},"2b04":function(t,n,a){"use strict";a.r(n);var u=a("4bbf"),e=a.n(u);for(var c in u)"default"!==c&&function(t){a.d(n,t,(function(){return u[t]}))}(c);n["default"]=e.a},"4bbf":function(t,n,a){"use strict";(function(t){Object.defineProperty(n,"__esModule",{value:!0}),n.default=void 0;var a={data:function(){return{StatusBar:this.StatusBar,CustomBar:this.CustomBar}},name:"cu-custom",computed:{style:function(){var t=this.StatusBar,n=this.CustomBar,a=this.bgImage,u="height:".concat(n,"px;padding-top:").concat(t,"px;");return this.bgImage&&(u="".concat(u,"background-image:url(").concat(a,");")),u}},props:{bgColor:{type:String,default:""},isBack:{type:[Boolean,String],default:!1},bgImage:{type:String,default:""}},methods:{BackPage:function(){t.navigateBack({delta:1})}}};n.default=a}).call(this,a("543d")["default"])},"5dc3":function(t,n,a){"use strict";a.r(n);var u=a("2608"),e=a("2b04");for(var c in e)"default"!==c&&function(t){a.d(n,t,(function(){return e[t]}))}(c);var r,o=a("f0c5"),i=Object(o["a"])(e["default"],u["b"],u["c"],!1,null,null,null,!1,u["a"],r);n["default"]=i.exports}}]);
;(global["webpackJsonp"] = global["webpackJsonp"] || []).push([
    'plugin/colorui/components/cu-custom-create-component',
    {
        'plugin/colorui/components/cu-custom-create-component':(function(module, exports, __webpack_require__){
            __webpack_require__('543d')['createComponent'](__webpack_require__("5dc3"))
        })
    },
    [['plugin/colorui/components/cu-custom-create-component']]
]);
