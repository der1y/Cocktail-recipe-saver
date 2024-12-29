import { createRouter as createRouter, createWebHistory } from 'vue-router'
import { useStore } from 'vuex'

// Import components
import HomeView from '../views/HomeView.vue'
import LoginView from '../views/LoginView.vue'
import LogoutView from '../views/LogoutView.vue'
import RegisterView from '../views/RegisterView.vue'
import CocktailDetailsView from '../views/CocktailDetailsView.vue'
import AddCocktailView from '../views/AddCocktailView.vue'
import NotFoundView from '../views/NotFoundView.vue'
import EditCocktailView from '../views/EditCocktailView.vue'
import NoAccessView from '../views/NoAccessView.vue'
import UserCocktailsView from '../views/UserCocktailsView.vue'
import PublicUserCocktailsView from '../views/PublicUserCocktailsView.vue'
//soy una mala influencia para mis companeros <3



/**
 * The Vue Router is used to "direct" the browser to render a specific view component
 * inside of App.vue depending on the URL.
 *
 * It also is used to detect whether or not a route requires the user to have first authenticated.
 * If the user has not yet authenticated (and needs to) they are redirected to /login
 * If they have (or don't need to) they're allowed to go about their way.
 */
const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView,
    meta: {
      requiresAuth: true
    }
  },
  {
    path: "/login",
    name: "login",
    component: LoginView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/logout",
    name: "logout",
    component: LogoutView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/register",
    name: "register",
    component: RegisterView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/cocktails/:cocktailId",
    name: 'CocktailDetails',
    component: CocktailDetailsView
  },
  {
    path: '/create',
    name: 'AddCocktail',
    component: AddCocktailView
  },
  {
    path: '/cocktail/:cocktailId/edit',
    name: 'EditCocktail',
    component: EditCocktailView
  },
  {
    path: '/users/:userId/cocktails',
    name: 'UserCocktails',
    component: PublicUserCocktailsView,
  },
  {
    path: '/not-found',
    name: 'NotFoundView',
    component: NotFoundView
  },
  {
    path: '/no-access',
    name: 'NoAccessView',
    component: NoAccessView
  },
  {
    path: '/users',
    name: 'PublicUsers',
    component: UserCocktailsView,
    meta: {
      requiresAuth: true
    }
  }
];

// Create the router
const router = createRouter({
  history: createWebHistory(),
  routes: routes
});

router.beforeEach((to) => {

  // Get the Vuex store
  const store = useStore();

  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && store.state.token === '') {
    return { name: "login" };
  }
  // Otherwise, do nothing and they'll go to their next destination
});

export default router;
