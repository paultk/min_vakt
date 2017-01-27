"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var core_1 = require('@angular/core');
var platform_browser_1 = require('@angular/platform-browser');
var forms_1 = require('@angular/forms');
var http_1 = require('@angular/http');
var ng_bootstrap_1 = require('@ng-bootstrap/ng-bootstrap');
var app_component_1 = require('./app.component');
var navigation_component_1 = require('./navigation.component');
var profil_component_1 = require('./profil.component');
var userinfo_component_1 = require('./userinfo.component');
var login_component_1 = require('./login.component');
var user_form_component_1 = require('./user-form.component');
var user_service_1 = require('./user.service');
var fravaer_service_1 = require('./fravaer.service');
var avdeling_service_1 = require('./avdeling.service');
var input_field_component_1 = require('./input-field.component');
var forgot_credentials_component_1 = require('./forgot-credentials.component');
var fravaer_component_1 = require('./fravaer.component');
var faq_component_1 = require("./faq.component");
var calendar_component_1 = require("./calendar.component");
var nav_bar_component_1 = require("./nav-bar.component");
var shift_service_1 = require("./shift.service");
// import "./rxjs-extensions";
var notification_component_1 = require("./notification.component");
var notification_service_1 = require("./notification.service");
var authentication_service_1 = require('./authentication.service');
var user_search_component_1 = require('./user-search.component');
var app_routing_module_1 = require('./app-routing.module');
var AppModule = (function () {
    function AppModule() {
    }
    AppModule = __decorate([
        core_1.NgModule({
            imports: [
                ng_bootstrap_1.NgbModule.forRoot(),
                platform_browser_1.BrowserModule,
                forms_1.FormsModule,
                forms_1.ReactiveFormsModule,
                http_1.HttpModule,
                app_routing_module_1.AppRoutingModule
            ],
            declarations: [
                app_component_1.AppComponent,
                navigation_component_1.NavigationComponent,
                login_component_1.LoginComponent,
                userinfo_component_1.UserinfoComponent,
                profil_component_1.ProfilComponent,
                user_form_component_1.UserFormComponent,
                input_field_component_1.InputFieldComponent,
                forgot_credentials_component_1.ForgotCredentialsComponent,
                fravaer_component_1.FravaerComponent,
                faq_component_1.FaqComponent,
                calendar_component_1.CalendarComponent,
                nav_bar_component_1.NavBarComponent,
                faq_component_1.FaqComponent,
                notification_component_1.NotificationComponent,
                user_search_component_1.UserSearchcomponent
            ],
            providers: [
                user_service_1.UserService,
                fravaer_service_1.FravaerService,
                shift_service_1.ShiftService,
                fravaer_service_1.FravaerService,
                avdeling_service_1.AvdelingService,
                notification_service_1.NotificationService,
                authentication_service_1.AuthenticationService
            ],
            bootstrap: [app_component_1.AppComponent]
        }), 
        __metadata('design:paramtypes', [])
    ], AppModule);
    return AppModule;
}());
exports.AppModule = AppModule;
//# sourceMappingURL=app.module.js.map