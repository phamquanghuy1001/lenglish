import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Config } from './config.model';
import { ConfigPopupService } from './config-popup.service';
import { ConfigService } from './config.service';

@Component({
    selector: 'jhi-config-delete-dialog',
    templateUrl: './config-delete-dialog.component.html'
})
export class ConfigDeleteDialogComponent {

    config: Config;

    constructor(
        private configService: ConfigService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.configService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'configListModification',
                content: 'Deleted an config'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-config-delete-popup',
    template: ''
})
export class ConfigDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private configPopupService: ConfigPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.configPopupService
                .open(ConfigDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
