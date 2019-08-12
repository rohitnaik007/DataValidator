import React from 'react';
import '../styles/App.css';
import {Component} from 'react';
import 'bootstrap/dist/css/bootstrap.css';

class Display extends Component{
    state = {
        dupData: null
      }
      componentDidMount() {

        fetch('http://localhost:8080/api/data')
        .then(res => res.json())
        .then((data) => {
          this.setState({ dupData: data })
          console.log(this.state.dupData)
        })
        .catch(console.log('Error getting data...'));


      }
      render() {
        return (<div></div>
        );
      }
}

export default Display;
