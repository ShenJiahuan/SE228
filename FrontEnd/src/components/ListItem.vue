<template>
    <el-card shadow="hover" class="item">
        <div class="item-img">
            <img :src="this.$store.state.config.backend + 'images/' + book.img" />
        </div>
        <div class="item-desc">
            <h3 class="item-title">
                <router-link v-bind:to="'/info/' + book.bookId">{{book.title}}</router-link>
            </h3>
            <span class="key" v-if="book.author != null">作者：</span>
            <span class="value" v-if="book.author != null">{{book.author}}</span>
            <br v-if="book.author != null">
            <span class="key" v-if="book.publisher != null">出版社：</span>
            <span class="value" v-if="book.publisher != null">{{book.publisher}}</span>
            <br v-if="book.publisher != null">
            <span class="key">ISBN：</span>
            <span class="value">{{book.isbn}}</span>
            <br>
            <span class="key">定价：</span>
            <span class="value">
                <span class="price">¥{{book.price}}</span>
            </span>
            <br>
        </div>
    </el-card>
</template>

<script>
    export default {
        name: "ListItem",
        props: [
            "book",
        ]
    }
</script>

<style scoped>
    .item {
        width: calc(50% - 52px);
        border-bottom: 1px #EBEEF5 solid;
        margin: 5px;
        padding: 20px;
    }

    .item-img {
        width: 120px;
        height: 160px;
    }

    .item-img img {
        max-width: 100%;
        max-height: 100%;
        border-radius: 2px;
    }

    .item-img, .item-desc {
        display: table-cell;
        vertical-align: top;
    }

    .item-desc {
        text-align: left;
        padding-left: 20px;
    }

    .item-title {
        margin: 5px 0;
    }

    .price {
        color: #ff0036;
        font-size: 20px;
    }
</style>
