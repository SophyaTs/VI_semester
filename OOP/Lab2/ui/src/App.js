import './style.css';
import { BrowserRouter, Route, Switch, Link } from 'react-router-dom';
import ManagerWorkspace from './ManagerWorkspace.jsx'
import DeveloperWorkspace from './DeveloperWorkspace.jsx'
import Header from './Header'
import Secured from './Secured'
import Keycloak from 'keycloak-js';
import { Component } from "react";


function App() {
  return (
    <BrowserRouter>
      <div className = "App">
        <Header/>
        <div class = 'main'>
        <Switch>
          <Route exact path = '/' component = {Secured}/>
          {/* <Route path = '/mng' component = {ManagerWorkspace}/>
          <Route path = '/dev' component = {DeveloperWorkspace}/> */}
        </Switch>

          {/* <ul>
            <li><Link to="/">public component</Link></li>
            <li><Link to="/secured">secured component</Link></li>
          </ul>
          <Route exact path="/" component={Welcome} />
          <Route path="/secured" component={Secured} /> */}
        </div>
      </div>
    </BrowserRouter>
  );
}

class Welcome extends Component {
  render() {
    return (
      <div className="Welcome">
        <p>This is your public-facing component.</p>
      </div>
    );
  }
}

export default App;
