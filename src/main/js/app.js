'use strict';

const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client');

class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {urls: []};
	}

	componentDidMount() {
		client({method: 'GET', path: '/api/urls'}).done(response => {
			this.setState({urls: response.entity._embedded.urls});
		});
	}

	render() {
		return (
			<URLList urls={this.state.urls}/>
		)
	}
}

class URLList extends React.Component{
	render() {
		const urls = this.props.urls.map(url =>
			<URL key={url._links.self.href} url={url}/>
		);
		return (
			<table>
				<tbody>
					<tr>
						<th>Long URL</th>
						<th>Short URL</th>
					</tr>
					{urls}
				</tbody>
			</table>
		)
	}
}

class URL extends React.Component{
	render() {
		return (
			<tr>
				<td><a href={this.props.url.longurl}>{this.props.url.longurl}</a></td>
				<td><a href={this.props.url._links.self.href}>{this.props.url._links.self.href}</a></td>
			</tr>
		)
	}
}

ReactDOM.render(
	<App />,
	document.getElementById('react')
)