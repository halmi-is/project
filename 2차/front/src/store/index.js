import { createStore } from 'vuex'

const user = JSON.parse(localStorage.getItem("user"));
export default createStore({
  state: {
    loggedIn : user!=null? true : false 
  },
  
})
