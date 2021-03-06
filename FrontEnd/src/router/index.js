import Vue from "vue";
import Router from "vue-router";
import Index from "@/view/Index";
import BookList from "@/view/BookList";
import BookInfo from "@/view/BookInfo";
import NotFound from "@/view/NotFound";
import Register from "@/view/Register";
import Login from "@/view/Login";
import Cart from "@/view/Cart";
import Purchased from "@/view/Purchased";
import UserManagement from "@/view/UserManagement";
import CreateBook from "@/view/CreateBook";
import EditBook from "@/view/EditBook";
import BookManagement from "@/view/BookManagement";
import OrderStatus from "@/view/OrderStatus";
import OrderManagement from "@/view/OrderManagement";

Vue.use(Router);

var router = new Router({
  routes: [
    {
      path: "/",
      redirect: "/index/recommend",
    },
    {
      path: "/index",
      redirect: "/index/recommend",
    },
    {
      path: "/index/:choice",
      name: "index",
      component: Index,
      meta: {
        title: "e-Book 在线书店 - 首页"
      }
    },
    {
      path: "/list/:keyword",
      name: "booklist",
      component: BookList,
      meta: {
        title: "e-Book 在线书店 - 图书列表"
      }
    },
    {
      path: "/info/:id",
      name: "bookinfo",
      component: BookInfo,
      meta: {
        title: "e-Book 在线书店 - 图书详情"
      }
    },
    {
      path: "/register",
      name: "register",
      component: Register,
      meta: {
        title: "e-Book 在线书店 - 用户注册"
      }
    },
    {
      path: "/login",
      name: "login",
      component: Login,
      meta: {
        title: "e-Book 在线书店 - 用户登录"
      }
    },
    {
      path: "/cart",
      name: "Cart",
      component: Cart,
      meta: {
        title: "e-Book 在线书店 - 购物车"
      }
    },
    {
      path: "/purchased",
      name: "Purchased",
      component: Purchased,
      meta: {
        title: "e-Book 在线书店 - 已购"
      }
    },
    {
      path: "/user",
      name: "UserManagement",
      component: UserManagement,
      meta: {
        title: "e-Book 在线书店 - 用户管理"
      }
    },
    {
      path: "/book/create",
      name: "CreateBook",
      component: CreateBook,
      meta: {
        title: "e-Book 在线书店 - 添加书籍"
      }
    },
    {
      path: "/book/edit/:id",
      name: "EditBook",
      component: EditBook,
      meta: {
        title: "e-Book 在线书店 - 编辑书籍"
      }
    },
    {
      path: "/book/manage",
      name: "BookManagement",
      component: BookManagement,
      meta: {
        title: "e-Book 在线书店 - 书籍管理"
      }
    },
    {
      path: "/order/manage",
      name: "OrderStatus",
      component: OrderStatus,
      meta: {
        title: "e-Book 在线书店 - 销量管理"
      }
    },
    {
        path: "/allorders",
        name: "OrderManagement",
        component: OrderManagement,
        meta: {
          title: "e-Book 在线书店 - 订单管理"
      }
    },
    {
      path: "/404",
      name: "404",
      component: NotFound,
      meta: {
        title: "e-Book 在线书店 - 找不到页面"
      }
    },
    {
      path: "*",
      redirect: "/404"
    }
  ]
});

router.beforeEach((to, from, next) => {
  if (to.meta.title) {
    document.title = to.meta.title;
  }
  next()
});

router.afterEach((to, from, next) => {
  window.scrollTo(0, 0);
});


export default router;
