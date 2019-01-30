import { html, PolymerElement } from '../libs/@polymer/polymer/polymer-element.js';
import '../libs/@polymer/app-layout/app-layout.js'
import '../libs/@polymer/paper-icon-button/paper-icon-button.js';
import '../libs/@polymer/iron-icons/iron-icons.js';
import '../libs/@polymer/paper-styles/paper-styles.js';

class MainView extends PolymerElement {
    static get template() {
        return html`
            <link rel="stylesheet" href="css/example.css">
            <style>
                app-header {
                    background-color: var(--dark-theme-background-color);
                    color: white;
                }     
                .content {
                    flex: 1;
                    background-color: var(--dark-theme-secondary-color);
                }
                footer {
                    text-align: center;
                }       
            </style>
            <app-header-layout fullbleed>
            <app-header>
                <app-toolbar>
                    <paper-icon-button icon="icons:reorder"></paper-icon-button>
                </app-toolbar>
            </app-header>
            <div class="content">
                <div class="square gray-200"></div>
                <div class="square gray-400"></div>
                <div class="square gray-800"></div>
            </div>
            <footer>© artfable</footer>
            </app-header-layout>
        `
    }
}

customElements.define("main-view", MainView);
console.log(document.createElement("main-view"));