import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CloudForm } from './cloud-form';

describe('CloudForm', () => {
  let component: CloudForm;
  let fixture: ComponentFixture<CloudForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CloudForm]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CloudForm);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
