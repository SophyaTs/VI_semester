import { Component } from 'react';
import $, { ajax, post } from "jquery";
import {user} from './globalvars.jsx'

class Form extends Component{
    constructor(props){
      super(props);
      this.state = {
        login: '',
        password: ''
      };
      this.onHandleChangeLogin = this.onHandleChangeLogin.bind(this);
      this.onHandleChangePassword = this.onHandleChangePassword.bind(this);
      this.componentDidMount = this.componentDidMount.bind(this);
    }
  
    componentDidMount(){
      localStorage.setItem("username",'');
      $(document).on("submit", "#loginform", function(event) {
        
  
        $.post({
          url:'http://localhost:8080/Lab1/index',
          contentType:/* 'application/x-www-form-urlencoded; charset=UTF-8' */ "application/json", // NOT dataType!
          /* headers: {
            'Access-Control-Allow-Origin': 'http://localhost:3000',
            'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8' 
          }, */
          data: JSON.stringify({
            login: this.state.login,
            password: this.state.password
          }),
          success: function(responseJSON){
            var resp = JSON.parse(responseJSON)
            if (resp.status === false)
              $("#errormsg").text("Login or password was incorrect");
            else{
              localStorage.setItem("username",resp.name);
              window.location.href = '/d_u=' + this.state.login;                      
            }
          }.bind(this),
        });
         
        event.preventDefault(); // Important! Prevents submitting the form.
        this.setState({
          password: ""
          });
      }.bind(this));
    }
  
    onHandleChangeLogin(e){
      this.setState({
        login: e.target.value
      });
    }
    onHandleChangePassword(e){
      this.setState({
        password: e.target.value
      });
    }
  
    render() {
      return(
        <div>
          <div id = "errormsg"></div>
          <form id = "loginform"> 
            <label>Name:
              <input 
                type = "text" 
                name = "login"
                onChange = {this.onHandleChangeLogin}
                value = {this.state.login}
              /><br />
            </label>
  
            <label>Password:
            <input 
              type = "password" 
              name = "password" 
              onChange = {this.onHandleChangePassword}
              value = {this.state.password}
            /><br />
            </label>
            
            <button type="submit">Submit</button>
          </form>
        </div>
      )
    }
}

export default Form;