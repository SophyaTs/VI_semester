import { Component } from "react";
import Greeting from "./Greeting";
import $, { get, ajax } from "jquery";
import Keycloak from 'keycloak-js';

class ManagerWorkspace extends Component{
    constructor(props){
        super(props)
        this.state = {
            showChooseProject: true,
            showTaskMenu: false,
            showChooseTask: true,
            showTasks: false,
            showDevs: false,
            showWarning: false,
            projectId: 0,
            taskId: 0,
            total: 0,
            cost: '---',
            required: 0,
        };
        this.render = this.render.bind(this);
        this.renderChooseProject = this.renderChooseProject.bind(this);
        this.renderChooseTask = this.renderChooseTask.bind(this);
        this.componentDidMount = this.componentDidMount.bind(this);
        this.onHandleChange = this.onHandleChange.bind(this);
    }

    renderChooseProject(){
        return (
            this.state.showChooseProject ?
            <option name = 'projects' value = '0'>Choose Project</option> : null
        )
    }

    renderChooseTask(){
        return(
            this.state.showChooseTask ?
            '<option name = "tasks" value = "0">Choose Task</option>' : null
        )
    }

    componentDidMount(){
                // fetch all projects
                $.get({
                    url: 'http://localhost:8080/projects',
                    success: function(responseJSON){
                        let select=$('#projects');
                        $.each(responseJSON, function(index, item) { // Iterate over the JSON array (projects)
                            select.append('<option name="projects" value="'+item.id+'">'+item.name+'</option>');
                        }.bind(this));
                    }.bind(this),
                    error: function(){
                        //window.location.href = '/';
                    },
                });

        // get tasks for chosen project
        $(document).on("change", "#projects", function(event){
            var newProject = $('#projects').find(":selected").val();
            this.setState({
                showChooseProject: false,
                showTaskMenu: true,
                showChooseTask: true,
                showDevs: false,
                projectId: newProject,
                cost: '---',
            });
            $.get({
                url: 'http://localhost:8080/projects/'+this.state.projectId+'/tasks',
                success: function(responseJSON){
                    let select=$('#tasks');
                    select.empty();
                    select.append(this.renderChooseTask()); 
                    $.each(responseJSON, function(index, item) { // Iterate over the JSON array (tasks)
                        select.append('<option name="tasks" value="'+item.id+'">'+item.name+'</option>');
                    }.bind(this));
                }.bind(this),
            });
        }.bind(this));
        
        // get available developers for chosen task
        $(document).on("change", "#tasks", function(event){
            var newTask = $('#tasks').find(":selected").val();
            this.setState({
                showChooseProject: false,
                showChooseTask: false,
                showDevs: true,
                taskId: newTask,
            });
            $("#devs").empty();
            $('option[value="0"]').remove();
            if(this.state.taskId != 0){
                $.get({
                    url: 'http://localhost:8080/projects/'+this.state.projectId+'/tasks/'+this.state.taskId+'/all',
                    success: function(responseJSON){
                        // render all developers
                        $.each(responseJSON, function(index,item){
                            $("#devs").append(
                                '<tr>'+
                                '<td class="devtable skinny"><input class = "skinny" type="checkbox" id="d'+item.id+'" value="'+item.id+'"></td>'+
                                '<td class="devtable skinny">'+item.id+'</td>'+
                                '<td class="devtable large">'+item.name+'</td>'+
                                '<td class="devtable skinny">'+item.salary+'</td>'+
                                '<td class="devtable skinny" id="dh'+item.id+'">0</td>'+
                                '</tr>'                            
                            );
                            var idStr = "d" + item.id;
                            $(document).on("change",document.getElementById(idStr),this.onHandleChange);
                        }.bind(this));                      
                    }.bind(this),
                });

                $.get({
                    url: 'http://localhost:8080/projects/'+this.state.projectId+'/tasks/'+this.state.taskId+'/working',
                    success: function(responseJSON){                       
                        var size = 0;
                        $.each(responseJSON, function(index,item){
                            // fill their hours
                            var idStr = "dh" + item.employee.id;
                            $(document.getElementById(idStr)).text(item.hrs);

                            // check chosen developers
                            if(item.active){
                                idStr = "d" + item.employee.id;
                                $(document.getElementById(idStr)).prop('checked',true);
                                size+=1;
                            }
                        }.bind(this));
                      
                        this.setState({
                            total: size
                        });
                    }.bind(this)
                });

                $.get({
                    url: 'http://localhost:8080/projects/'+this.state.projectId+'/tasks/'+this.state.taskId+'/req',
                    success: function(response){
                        this.setState({
                            required: response
                        });
                    }.bind(this)
                });
            }
        }.bind(this));

        $(document).on("click", "#savebtn", function(event){
            let selected = $('#devs').find(":checked");
            let ids = [];
            $.each(selected, function(index,item){
                ids.push($(item).val());
            });
            $.ajax({
                type: "PUT",
                url: 'http://localhost:8080/projects/'+this.state.projectId+'/tasks/'+this.state.taskId+'/save',
                contentType: "application/json",
                data: JSON.stringify(ids), 
                success: function(){}            
            });
        }.bind(this));

        // calculate cost
        $(document).on("click", "#calcbtn", function(event){
            $.get({
                url: 'http://localhost:8080/calc/'+this.state.projectId,
                success: function(response){
                    this.setState({
                        cost: String(response)
                    });
                }.bind(this),                
            });
        }.bind(this));
    }

    onHandleChange(e){
        let selected  = $('#devs').find(":checked")
        var size = $(selected).length;
        if(size > this.state.required){
            this.setState({
                total: size,
                showWarning: true
            });
        } else {
            this.setState({
                total: size,
                showWarning: false
            });
        }  
    }

    render(){
                return(
                    <div>
                        <Greeting keycloak={this.state.keycloak}/>

                        <table class = 'grid'>
                            <tbody>
                                <tr>
                                    <td class = 'gridline labelcol'>Project:</td>
                                    <td class = 'gridline inputcol'>
                                        <select id = "projects">{this.renderChooseProject()}</select><br/>
                                    </td>
                                </tr>
                                { this.state.showTaskMenu ? <tr>
                                    <td class = 'gridline labelcol'>Task:</td>
                                    <td class = 'gridline inputcol'>
                                        <select id = "tasks"></select>
                                    </td>
                                </tr> : null}

                                { this.state.showDevs ? <tr>
                                    <td colSpan='2'>
                                        <p>Developers required: {this.state.required}</p><br/>
                                        <center>
                                            <table class = 'devtable' >
                                                <thead>
                                                    <th class='devtable'>Choosen</th>
                                                    <th class='devtable'>ID</th>
                                                    <th class='devtable'>Name</th>
                                                    <th class='devtable'>Salary</th> 
                                                    <th class='devtable'>Hours spent</th>                               
                                                </thead>
                                            <tbody id = "devs"></tbody>
                                            </table>
                                        </center><br/>
                                        <p>Total: {this.state.total} </p>{this.state.showWarning ? <p class='errormsg'>Too many developers!</p> : null}<br/>
                                    </td>
                                </tr> : null}

                                { this.state.showTaskMenu ? <tr>
                                    <td class = 'gridline labelcol'><p>Current cost: {this.state.cost}$ </p> <button id = "calcbtn">Calculate</button></td>
                                    <td class = 'gridline inputcol'></td>
                                </tr> : null}

                                <tr>
                                    <td colSpan='2'>
                                        <center><button id = "savebtn">Save</button></center>
                                    </td>
                                </tr>

                            </tbody>
                        </table>
                    </div>
                )

    }
}

export default ManagerWorkspace;