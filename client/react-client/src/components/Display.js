import React from 'react';
import '../styles/App.css';
import {Component} from 'react';
import 'bootstrap/dist/css/bootstrap.css';

class Display extends Component{
    state = {
        dupData: null
      }
      componentDidMount() {

        fetch('http://localhost:8080/api/dataAdvanced')
        .then(res => res.json())
        .then((data) => {
          this.setState({ dupData: data })
          console.log(this.state.dupData)
        })
        .catch(console.log('Error getting data...'));


      }
      render() {
        return (
            <div className="container">
           
            <h1>Validated Data</h1>
            <div className="table-responsive">          
             <table className="table table-bordered">
             <thead className="thead-dark">
                <tr>
                     <th>Duplicate Data</th>
                </tr>
            </thead>
            <tbody>
            {!this.state.dupData ? <tr></tr> : this.state.dupData.duplicates.map((data,i) => (
              <tr  key={i}>
               
                  <td>{data}</td>
                  
              </tr>
           ))}
            </tbody>
            </table>
           </div>
           <div className="table-responsive">          
             <table className="table table-bordered">
             <thead className="thead-dark">
                <tr>
                     <th>Non-Duplicate Data</th>
                </tr>
            </thead>
            <tbody>
            {!this.state.dupData ? <tr></tr> : this.state.dupData.nonDuplicates.map((data,i) => (
              <tr  key={i}>
               
              <td>{data}</td>
              
          </tr>
           ))}
            </tbody>
            </table>
           </div>
           </div>
        ); 
      }
}

export default Display;
