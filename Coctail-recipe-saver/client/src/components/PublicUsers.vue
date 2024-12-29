<template>
    <span id="container">
        <section id="title-search">
            <h2 id="title"> Community Cocktails</h2>
            <section id="search">
                <search-box />
            </section>
        </section>

        <div id="users">
            <router-link class="user-decoration" v-for="user in users" :key="user.id"
                :to="{ name: 'UserCocktails', params: { userId: user.id }, query: { name: user.username}}">
                <UserCard class="user" :user="user" />
            </router-link>
        </div>
    </span>
</template>

<script>
import SearchBox from './SearchBox.vue';
import UserCard from './UserCard.vue';

export default {
    components: {
        SearchBox,
        UserCard
    },
    props: ['users']
}
</script>

<style scoped>
#container {
    display: flex;
    justify-content: flex-start;
    position: absolute;
    flex-wrap: wrap;
    gap: 10px;
    width: 100vw;
    padding: 0px 150px;
    top: 100px;
    left: 0px;
}

#users {
    display: flex;
    flex-direction: column;
    width: 100vw;
}

.user {
    padding: 10px 0px;
}

.user:hover {
    cursor: pointer;

}

.user-decoration {
    text-decoration: none;
}

#title-search {
    display: flex;
    flex-direction: row;
    justify-content: flex-end;
    align-items: center;
    width: 100vw;
    z-index: 1;
    padding: 0px 20px;
    border-bottom: 1px solid #7A9598;
}

#title {
    font-size: 25px;
    padding: 20px 0px;
    margin: 0;
    color: #283618;
    display: flex;
    justify-content: flex-start;
}

#search {
    display: flex;
    align-items: center;
    z-index: 1;
    justify-content: flex-end;
    margin-left: auto;
    color: #283618;
}
</style>