import './style.css';
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import Form from './Form.jsx'
import ManagerWorkspace from './ManagerWorkspace.jsx'
import DeveloperWorkspace from './DeveloperWorkspace.jsx'
import Header from './Header'


function App() {
  return (
    <BrowserRouter>
      <div className = "App">
        <Header/>
        <div class = 'main'>
        <Switch>
          <Route exact path = '/' component = {Form}/>
          <Route path = '/m_u=*' component = {ManagerWorkspace}/>
          <Route path = '/d_u=*' component = {DeveloperWorkspace}/>
        </Switch>
        </div>
      </div>
    </BrowserRouter>
  );
}

export default App;
