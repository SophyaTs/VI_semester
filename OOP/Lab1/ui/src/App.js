import './App.css';
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import Form from './Form.jsx'
import ManagerWorkspace from './ManagerWorkspace.jsx'
import DeveloperWorkspace from './DeveloperWorkspace.jsx'


function App() {
  return (
    <BrowserRouter>
      <div className = "App">
        <Switch>
          <Route exact path = '/' component = {Form}/>
          <Route path = '/m_u=*' component = {ManagerWorkspace}/>
          <Route path = '/d_u=*' component = {DeveloperWorkspace}/>
        </Switch>
      </div>
    </BrowserRouter>
  );
}

export default App;
