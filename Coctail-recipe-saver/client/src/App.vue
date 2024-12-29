<template>
  <div id="cart-app">
    <div class="nav-box">
      <header class="title">Cocktail Book.</header>

      <nav>
        <router-link class="nav-style" :to="{ name: 'home' }">Home</router-link>
        <router-link class="nav-style" :to="{ name: 'PublicUsers' }">Community</router-link>
        <router-link class="nav-style" v-bind:to="{ name: 'logout' }" v-if="$store.state.token">
          Logout
        </router-link>
        <!-- <router-link v-bind:to="{ name: 'login' }" v-else>Login</router-link> -->
      </nav>
    </div>

    <main>
      <router-view />
    </main>
    <footer></footer>
  </div>
</template>

<style scoped>
body {
  margin: 0;
}

.nav-box {
  display: flex;
  align-items: center;
  justify-content: center;
  position: fixed;
  color: #FEFAE0;
  height: 100px;
  width: 100%;
  top: 0;
  left: 0;
  z-index: 2;
  /* border-bottom: 1px solid #7A9598; */
}

#cart-app {
  display: flex;
  flex-direction: column;
  height: 100vh;
  box-sizing: border-box;
  padding: 8px;
}

main {
  flex-grow: 1;
  display: flex;
  align-self: center;
  z-index: 1;
}

header {
  position: fixed;
  z-index: 3;
  font-size: 2rem;
  font-weight: 900;
  top: 35px;
  left: 90px;
  color: #283618;

}

nav {
  position: fixed;
  display: flex;
  justify-content: flex-end;
  width: 100%;
  right: 90px;
  top: 40px;
  
}

.nav-style {
  display: flex;
  flex-wrap: wrap;
  font-size: 1rem;
  text-decoration: none;
  color: #283618;
}

.nav-style:not(:first-child) {
  padding-left: 20px
}

.nav-style:not(:last-child) {
  border-right: 1px solid #7A9598;
  padding-right: 20px;
  padding-left: 20px;
}
</style>