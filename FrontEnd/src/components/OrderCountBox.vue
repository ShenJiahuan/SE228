<template>
  <el-input-number
    :class="{'order-count-mini': this.size === 'mini', 'order-count-medium': this.size === 'medium'}"
    name="count"
    :size="this.size"
    :value="num"
    @change="this.setNonNull"
    autocomplete="off"
    :min="parseInt(this.min)"
    :max="parseInt(this.max)">
  </el-input-number>
</template>

<script>
  import Api from "@/components/Api.js";

  export default {
    name: "OrderCountBox",
    props: [
      "num",
      "setChosen",
      "size",
      "min",
      "max",
      "bookId",
    ],
    methods: {
      setNonNull(value) {
        if (value === undefined) {
          this.$emit("update:num", 2);
          this.$nextTick(() => {
            this.$emit("update:num", 1);
          });
        } else {
          this.$emit("update:num", value);
        }
        if (this.setChosen) {
          this.setChosen();
        }
        if (this.bookId != null) {
          console.log(this.bookId);
          let data = {
            cartItem:{
              id: this.bookId,
              count: value,
            }
          };
          console.log(data);
          Api.UpdateCart(data).then(
            response => {
              this.$notify({
                title: "成功",
                message: "更新购物车成功",
                type: "success"
              });
            }
          );
        }
      }
    }
  }
</script>

<style scoped>
  .order-count-mini {
    width: 100px;
  }

  .order-count-medium {
    width: 160px;
  }
</style>
