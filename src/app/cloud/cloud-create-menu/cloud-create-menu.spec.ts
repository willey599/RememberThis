import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CloudCreateMenu } from './cloud-create-menu';

describe('CloudCreateMenu', () => {
  let component: CloudCreateMenu;
  let fixture: ComponentFixture<CloudCreateMenu>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CloudCreateMenu]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CloudCreateMenu);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
