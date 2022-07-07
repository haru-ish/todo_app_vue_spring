<template>
  <div>
  <head>
    <meta charset="utf-8">
    <title>Vue.js App</title>
  </head>
  <body>
    <div id="app">
      <button @click="logout()">Logout</button>
      <h1>{{ info.userName }}'s Todo List</h1>
      <div class="tabs">
        <div v-for="label in labels" :key="label.value">
          <input type="radio" v-model="current" :value="label.value" :id=" 'radio-' + label.value">
          <label :for="'radio-' + label.value">{{ label.label }}</label>
        </div>
      </div>
      <table>
        <tbody>
          <tr v-for="(todo, index) in computedTodo" :key="todo.id" :class="{done : todo.state}">
            <td><input type="checkbox" v-model="todo.state"></td>
            <td>{{ todo.comment }}</td>
            <td><button @click="del(index, todo.id)">Delete</button></td>
          </tr>
        </tbody>
      </table>
      <h2>Add a new task</h2>
      <div style="color:red;" v-if="this.errorMsg">{{ this.errorMsg }}</div>
      <form @submit.prevent="add">
        <label>
          <div style="color:red;" v-if="v$.info.comment.maxLength.$invalid">{{ v$.info.comment.maxLength.$message }}</div>
          <input type="text" v-model="info.comment" @input="v$.info.comment.$touch">
        </label>
        <button :disabled="v$.$invalid" type="submit">Add</button>
      </form>
    </div>
  </body>
  </div>
</template>

<script>
import axios from 'axios'
import useVuelidate from '@vuelidate/core'
import { required, maxLength } from '@vuelidate/validators'

export default {
  name: 'Home',
  setup () {
    return { v$: useVuelidate() }
  },
  data () {
    return {
      errorMsg: '',
      info: {
        userId: 0,
        userName: this.$store.getters.getUserName || '',
        comment: '',
        state: false
      },
      todos: '',
      labels: [
        { value: 3, label: 'VIEW ALL' },
        { value: 2, label: 'ONGOING' },
        { value: 1, label: 'COMPLETED' }
      ],
      // Data to store the value of the selected labels
      // Set '3' as the initial value, means 'VIEW ALL'
      current: 3
    }
  },
  validations () {
    return {
      info: {
        comment: { required, maxLength: maxLength(100) }
      }
    }
  },
  created () {
    // Setting headers
    const headers = {
      headers: {
        'X-AUTH-TOKEN': 'Bearer ' + this.$store.getters.getToken
      }
    }

    // Check login status
    axios.get('http://localhost:8080/api/checkLoginStatus', headers)
      .then(res => {
        console.log(res)
      })
      .catch(e => {
        alert('ERROR: Login required')
        this.$router.push('/login')
        console.log(e)
      })

    // Get userID
    const d = new URLSearchParams()
    d.append('userName', this.info.userName)
    axios.post('http://localhost:8080/api/getUserId', d, headers).then(res => {
      this.info.userId = res.data
    })

    // Show a list
    this.$watch('info.userId', () => {
      this.getAllTodo()
    })
  },
  methods: {
    // Logout method
    logout: function () {
      if (confirm('Would you like to log out?')) {
        this.$store.dispatch('removeToken')
        this.$store.dispatch('removeUserName')
        this.$router.push('/login')
      }
    },
    // Add a new task
    add: function () {
      // Check for validation errors
      if (this.info.comment === '' || !this.info.comment.match(/\S/g) || this.info.comment.length > 100) {
        alert('Please enter your comment correctly.')
      } else {
        const d = new URLSearchParams()
        Object.keys(this.info).forEach(key => {
          d.append(key, this.info[key])
        })
        axios.post('http://localhost:8080/api/create', d, {
          headers: {
            'X-AUTH-TOKEN': 'Bearer ' + this.$store.getters.getToken
          }
        }).then(res => {
          // Result for validation errors from back-end
          if (res.data === 0) {
            this.errorMsg = 'There is an input error.'
          } else {
            // Show a list
            this.getAllTodo()
            this.info.comment = ''
          }
        })
      }
    },
    // Delete the task
    del: function (index, id) {
      const d = new URLSearchParams()
      d.append('userId', this.info.userId)
      d.append('id', id)
      axios.post('http://localhost:8080/api/delete', d, {
        headers: {
          'X-AUTH-TOKEN': 'Bearer ' + this.$store.getters.getToken
        }
      }).then(res => {
        this.todos.splice(index, 1)
      }).catch(e => {
        console.log(e)
      })
    },
    // Get All-TodoList
    getAllTodo: function () {
      axios.get('http://localhost:8080/api/getTodoAll', {
        headers: {'X-AUTH-TOKEN': 'Bearer ' + this.$store.getters.getToken},
        params: {
          userId: this.info.userId
        }
      }).then(res => {
        this.todos = res.data
      })
    }
  },
  computed: {
    computedTodo () {
      return Array.from(this.todos).filter(
        // (e) => !!((e.state ? 1 : 2) & this.current))
        (e) => (this.current === 3 ? true : !!((e.state ? 1 : 2) === this.current)))
    }
  }
}
</script>
