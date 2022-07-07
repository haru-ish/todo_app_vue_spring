<template>
    <div class="hello">
        <h1>{{ msg }}</h1>
      <form>
      <div>
        <label>Username</label>
        <input type="text" name="userName" v-model="userName" />
      </div>
      <div>
        <label>Password</label>
        <input type="password" name="password" v-model="password" >
      </div>
        <button type="button" @click="send">login</button>
    </form>
    </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'Login',
  data () {
    return {
      msg: 'Hello Customer.',
      userName: '',
      password: ''
    }
  },
  mounted () {
    // Check login status
    axios.get('http://localhost:8080/api/checkLoginStatus', {
      headers: {
        'X-AUTH-TOKEN': 'Bearer ' + this.$store.getters.getToken
      }
    })
      .then(res => {
        this.$router.push('/home')
      })
      .catch(e => {
        console.log('You need to log in.')
      })
  },
  methods: {
    send () {
      axios.post('http://localhost:8080/api/login', {
        userName: this.userName,
        password: this.password
      })
        .then(res => {
          this.$store.dispatch('saveToken', res.headers['x-auth-token'])
          this.$store.dispatch('saveUserName', this.userName)
          this.$router.push({ name: 'Home', params: { userName: this.userName } })
        })
        .catch(e => {
          alert('Login Fail')
          console.log(e)
        })
    }
  }
}
</script>
