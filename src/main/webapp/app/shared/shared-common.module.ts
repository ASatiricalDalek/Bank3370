import { NgModule } from '@angular/core';

import { Bank3370SharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [Bank3370SharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [Bank3370SharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class Bank3370SharedCommonModule {}
