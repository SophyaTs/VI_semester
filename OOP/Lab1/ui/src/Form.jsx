import './style.css';
import { Component } from 'react';
import $, { get, post } from "jquery";
import Cookies from 'universal-cookie';

const cookies = new Cookies();

class Form extends Component{
    constructor(props){
      super(props);
      this.state = {
        login: '',
        password: '',
        showError: false,
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
        cookies.remove("ROLE",{path:'/Lab1'});

        // create and validate new ones
        cookies.set("LOGIN",this.state.login, {path:'/Lab1'});
        cookies.set("PASSWORD",this.state.password, {path:'/Lab1'});
        $.get({
          url:'http://localhost:8080/Lab1/index',
          crossDomain: true,
          xhrFields: { withCredentials: true },
          success: function(response){
            if (response == null)
              this.setState({
                showError: true
              });
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
          <form id = "loginform"> 
          <table class = 'grid'>
            { this.state.showError ?
              <tr>
              <td class = 'errormsg' colSpan='2'>Login or password was incorrect</td>
            </tr> : null}
          
            <tr>
              <td class = 'gridline labelcol'>Name:</td>
              <td class = 'gridline inputcol'>
                <input 
                  type = "text" 
                  name = "login"
                  onChange = {this.onHandleChangeLogin}
                  value = {this.state.login}
                />
              </td>
            </tr>
  
            <tr>
              <td class = 'gridline labelcol'>Password:</td>
              <td class = 'gridline inputcol'>
                <input 
                  type = "password" 
                  name = "password" 
                  onChange = {this.onHandleChangePassword}
                  value = {this.state.password}
                />
              </td>
            </tr>    

            <tr>
              <td colSpan='2'>
                <center><button type="submit">Log in</button></center>
                </td>
            </tr>
                             
          </table>
          </form>
        </div>
      )
    }
}

export default Form;