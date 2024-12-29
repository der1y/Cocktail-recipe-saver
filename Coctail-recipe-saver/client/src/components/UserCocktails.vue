<template>
    <span class="container">
        <button type="button" @click="this.$router.push({ name: 'PublicUsers'})" v-if="this.$route.name === 'UserCocktails'">Go back</button>
        <section id="title-search">
            <h2 id="title" v-if="username">{{ username }}'s Cocktails</h2>
            <h2 id="title" v-else>My Cocktails</h2>
            <section id="search">
                <SearchBox />
            </section>
            <button class="add" type="button" @click="this.$router.push({name: 'AddCocktail'})" v-if="!username">+</button>
        </section>

        <div class="cocktails">
            <router-link class="cocktail-decoration" v-for="cocktail in cocktails" :key="cocktail.id"
                :to="{ name: 'CocktailDetails', params: { cocktailId: cocktail.id } }">
                <CocktailCard class="cocktail" :cocktail="cocktail" />
            </router-link>
        </div>
    </span>
</template>

<script>
import CocktailCard from './CocktailCard.vue';
import SearchBox from '../components/SearchBox.vue';

export default {
    data () {
        return {
            // Username is gotten if we are looking at a user who has public cocktails
            username: this.$route.query.name
        }
    },
    props: ['cocktails'],
    components: {
        CocktailCard,
        SearchBox
    }
}
</script>

<style scoped>
.add {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 30px;
    width: 30px;
    border: solid 1px #778742;
    border-radius: 15px;
    color: #778742;
    font-size: 1.5rem;
}
button:hover {
    cursor: pointer;
}
.cocktails {
    display: flex;
    justify-content: flex-start;
    position: absolute;
    flex-wrap: wrap;
    gap: 80px;
    width: 100vw;
    padding: 25px 150px;
    top: 100px;
    left: 0px;
}
.container {
    display: flex;
    justify-content: flex-start;
    position: absolute;
    flex-wrap: wrap;
    width: 100vw;
    padding: 0px 150px;
    top: 100px;
    left: 0px;
}

.cocktail:hover {
    cursor: pointer;
    box-shadow: 0 0px 10px #7A9598;
}

.cocktail-decoration {
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