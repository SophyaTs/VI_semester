import { Component } from "react";
import $, {get} from "jquery";
import Keycloak from 'keycloak-js';
import Greeting from "./Greeting";
import ManagerWorkspace from "./ManagerWorkspace";
import DeveloperWorkspace from "./DeveloperWorkspace";

class Secured extends Component {

    constructor(props) {
      super(props);
      this.state = { 
            keycloak: null, 
            authenticated: false ,
            exists: false, 
            loaded: false,
            role: '',
        };
        this.render = this.render.bind(this);
        this.loadUser = this.loadUser.bind(this);
        this.clearUser = this.clearUser.bind(this);
    }
  
    loadUser(){
        this.state.keycloak.loadUserInfo().then(function(userInfo) {
            localStorage.setItem("employeeLogin",this.state.keycloak.tokenParsed.preferred_username);

            $.get({
                url:'http://localhost:8080/user/'+localStorage.getItem("employeeLogin"),
                success: function (response) {
                    if(response){
                        this.setState({
                            exists: true,
                            loaded: true,
                        });
                        localStorage.setItem("employeeId",response.id);
                        localStorage.setItem("employeeName",response.name)
                        if(response.position.title === "manager"){
                            this.setState({role : "manager"});
                        }
                        else {
                            this.setState({role : "dev"});
                        }
                    } else {
                        this.setState({loaded: true,});
                        this.clearUser();
                    }
                }.bind(this)
            });
        }.bind(this));
    }

    clearUser(){
        this.setState({
            exists: false,
        });
        localStorage.setItem("employeeId",0);
        localStorage.setItem("employeeName","user");
        localStorage.setItem("employeeLogin", "");
    }

    componentDidMount() {
        const keycloak = Keycloak('/resources/keycloak.json');
        keycloak.init({onLoad: 'login-required'}).then( function(authenticated) {
            //localStorage.setItem("keycloak",keycloak);
            //localStorage.setItem("authenticated",authenticated);
            this.setState({ keycloak: keycloak, authenticated: authenticated });
        }.bind(this));

        
    }
  
    render() {
      if (this.state.keycloak) {
        if (this.state.authenticated) { 
            if (!this.state.loaded){
                this.loadUser();
            }           
            return (
                <div>                   
                    { this.state.exists ? 
                        <div>{this.state.role === 'manager'? <ManagerWorkspace keycloak={this.state.keycloak}/> : <DeveloperWorkspace keycloak={this.state.keycloak}/>} </div>
                        : <div><Greeting keycloak={this.state.keycloak}/><p>Oops! No such employee was found in the database! Try contacting administrator.</p></div>
                    }
                </div>
            );
        } 
        else {
            this.clearUser();
            this.setState({loaded: false});
            return (<div>Unable to authenticate!</div>)
        }
      }
      
      if(this.state.loaded){
        this.setState({loaded: false});
        this.clearUser();
      }
      return (          
        <div>Initializing Keycloak...</div>
      );
    }
  }

  export default Secured;