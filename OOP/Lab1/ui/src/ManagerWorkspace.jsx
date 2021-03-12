import { Component } from "react";
import Greeting from "./Greeting";
import $, { ajax, post } from "jquery";

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
            cost: 0,
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
        $.post({
            url: 'http://localhost:8080/Lab1/mng',
            contentType: "application/json",
            data: JSON.stringify({
                doGet: true,
                project: true,
            }),
            success: function(responseJSON){
                let select=$('#projects');
                $.each(responseJSON, function(index, item) { // Iterate over the JSON array (projects)
                    select.append('<option name="projects" value="'+item.id+'">'+item.name+'</option>');
                }.bind(this));
            }.bind(this)
        });

        // get tasks for chosen project
        $(document).on("change", "#projects", function(event){
            var newProject = $('#projects').find(":selected").val();
            this.setState({
                showChooseProject: false,
                showTaskMenu: true,
                showChooseTask: true,
                showDevs: false,
                projectId: newProject
            });
            $.post({
                url: 'http://localhost:8080/Lab1/mng',
                contentType: "application/json",
                data: JSON.stringify({
                    doGet: true,
                    project: false,
                    projectId: this.state.projectId
                }),
                success: function(responseJSON){
                    let select=$('#tasks');
                    select.empty();
                    select.append(this.renderChooseTask()); 
                    $.each(responseJSON, function(index, item) { // Iterate over the JSON array (tasks)
                        select.append('<option name="tasks" value="'+item.id+'">'+item.name+'</option>');
                    }.bind(this));
                }.bind(this)
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
            $("tbody").empty();
            $('option[value="0"]').remove();
            if(this.state.taskId != 0)
            $.post({
                url: 'http://localhost:8080/Lab1/mng',
                contentType: "application/json",
                data: JSON.stringify({
                    doGet: false,
                    forTask: true,
                    taskId: this.state.taskId
                }),
                success: function(responseJSON){
                    let allDevs = responseJSON.allDevs; // array of objects Employees
                    let chDevs = responseJSON.chDevs; // id of chosen Devs
                    let hours = responseJSON.hours; // map (id of Dev, spent hours) 

                    // render all developers
                    $.each(allDevs, function(index,item){
                        $("tbody").append(
                            '<tr>'+
                            '<td><input type="checkbox" id="d'+item.id+'" value="'+item.id+'"></td>'+
                            '<td>'+item.id+'</td>'+
                            '<td>'+item.name+'</td>'+
                            '<td>'+item.salary+'</td>'+
                            '<td id="dh'+item.id+'">0</td>'+
                            '</tr>'                            
                        );
                        var idStr = "d" + item.id;
                        $(document).on("change",document.getElementById(idStr),this.onHandleChange);
                    }.bind(this));

                    // fill their hours
                    $.each(hours,function(key,value){
                        var idStr = "dh" + key;
                        $(document.getElementById(idStr)).text(value);
                    }.bind(this));

                    // check chosen developers
                    $.each(chDevs, function(index,item){
                        var idStr = "d" + item;
                        $(document.getElementById(idStr)).prop('checked',true);
                    }.bind(this));

                    var size = $(chDevs).length
                    this.setState({
                        required: responseJSON.required,
                        total: size
                    });
                }.bind(this)
            });
        }.bind(this));

        // save changes
        $(document).on("click", "#savebtn", function(event){
            //TODO
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
                <Greeting/>
                <select id = "projects">{this.renderChooseProject()}</select><br/>
                { this.state.showTaskMenu ? 
                    <select id = "tasks"></select> : null
                }
                { this.state.showDevs ? 
                    <div>
                        <p>Developers required: {this.state.required}</p><br/>
                        <table id = "devs">
                            <thead>
                                <td>Choosen</td>
                                <td>ID</td>
                                <td>Name</td>
                                <td>Salary</td> 
                                <td>Hours spent</td>                               
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                        <p>Total: {this.state.total} </p>{this.state.showWarning ? <p>Too many developers!</p>: null}<br/>
                       {/* TODO  <p>Estimated cost: {this.state.cost}</p><br/> */}
                        <button id = "savebtn">Save</button>
                    </div> : null                  
                }
            </div>
        )
    }
}

export default ManagerWorkspace;