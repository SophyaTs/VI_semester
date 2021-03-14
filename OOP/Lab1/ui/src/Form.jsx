import { Component } from 'react';
import $, { get, post } from "jquery";
import Cookies from 'universal-cookie';

const cookies = new Cookies();

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
      $(document).on("submit", "#loginform", function(event) {
        //remove old credentials
        cookies.remove("LOGIN");
        cookies.remove("PASSWORD");
        cookies.remove("ROLE");

        // create and validate new ones
        cookies.set("LOGIN",this.state.login, {path:'/Lab1'});
        cookies.set("PASSWORD",this.state.password, {path:'/Lab1'});
        $.get({
          url:'http://localhost:8080/Lab1/index',
          crossDomain: true,
          xhrFields: { withCredentials: true },
          success: function(response){
            if (response == null)
              $("#errormsg").text("Login or password was incorrect");
            else {
              localStorage.setItem("username",response.name);
              localStorage.setItem("employee_id", response.id);
              if(response.manager === true)
                window.location.href = '/m_u=' + this.state.login;  
              else 
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