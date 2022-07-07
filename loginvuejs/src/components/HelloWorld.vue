<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
     <router-link to="/login">Login</router-link>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'HelloWorld',
  data () {
    return {
      msg: 'Please login.',
      userData: {
        id: '',
        name: '',
        password: '',
        submit: 'login'
      }
    }
  },
  methods: {
    send: function (e) {
      this.msg = 'waiting...'
      e.preventDefault()

      const d = new URLSearchParams()

      Object.keys(this.userData).forEach(key => {
        d.append(key, this.userData[key])
      })

      axios.post(e.target.action, d).then(res => {
        this.msg = res.data
      })
    }
    // getList: function () {
    //   axios.get('http://localhost:8080/getUserList').then(res => {
    //     this.users = res.data
    //     this.msg = 'You got the list!'
    //   })
    // }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
