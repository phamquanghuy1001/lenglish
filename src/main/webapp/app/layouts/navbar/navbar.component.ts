import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiLanguageService } from 'ng-jhipster';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { ProfileService } from '../profiles/profile.service';
import { CustomerUserService, CustomerUser } from '../../entities-app/customer-user';
import { JhiLanguageHelper, Principal, LoginModalService, LoginService, Account } from '../../shared';

import { VERSION } from '../../app.constants';
import { forEach } from '@angular/router/src/utils/collection';

@Component({
    selector: 'jhi-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: [
        'navbar.scss'
    ]
})
export class NavbarComponent implements OnInit {
    account: Account;
    inProduction: boolean;
    isNavbarCollapsed: boolean;
    languages: any[];
    swaggerEnabled: boolean;
    modalRef: NgbModalRef;
    version: string;
    eventSubscriber: Subscription;
    customerUser: CustomerUser;

    constructor(
        private loginService: LoginService,
        public customerUserService: CustomerUserService,
        private languageService: JhiLanguageService,
        private languageHelper: JhiLanguageHelper,
        private principal: Principal,
        private loginModalService: LoginModalService,
        private profileService: ProfileService,
        private router: Router,
        private eventManager: JhiEventManager
    ) {
        this.version = VERSION ? 'v' + VERSION : '';
        this.isNavbarCollapsed = true;
    }

    ngOnInit() {
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });
        this.profileService.getProfileInfo().subscribe((profileInfo) => {
            this.inProduction = profileInfo.inProduction;
            this.swaggerEnabled = profileInfo.swaggerEnabled;
        });
        this.eventSubscriber = this.eventManager.subscribe('getUser',
            (response) => {
                if (this.principal.isAuthenticated()) {
                    this.principal.identity().then((account) => {
                        this.account = account;
                    });
                }
                this.customerUserService.updateUser();
            });

        if (this.isAuthenticated()) {
            this.customerUserService.findCurrent().subscribe(
                (customerUser) => {
                    this.customerUser = customerUser;
                }
            );
        }
    }

    changeLanguage(languageKey: string) {
        this.languageService.changeLanguage(languageKey);
    }

    collapseNavbar() {
        this.isNavbarCollapsed = true;
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    logout() {
        this.collapseNavbar();
        this.loginService.logout();
        this.router.navigate(['']);
        this.account = null;
    }

    toggleNavbar() {
        this.isNavbarCollapsed = !this.isNavbarCollapsed;
    }

    getImageUrl() {
        return this.isAuthenticated() ? this.principal.getImageUrl() : null;
    }

    isLogin() {
        if (!this.isAuthenticated()) {
            return false;
        } else {
            for (const role of this.account.authorities) {
                if (role === 'ROLE_ADMIN') {
                    return true;
                }
            }
        }
        return false;
    }
}
